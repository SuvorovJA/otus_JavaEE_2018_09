package ru.otus.sua.L12.appSecure.presentation;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.omnifaces.cdi.Param;
import ru.otus.sua.L12.appSecure.ejbs.AccountStoreEJB;
import ru.otus.sua.L12.appSecure.ejbs.TfaGeneratorEJB;
import ru.otus.sua.L12.appSecure.entities.Account;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.Password;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Optional;

import static javax.security.enterprise.AuthenticationStatus.SEND_FAILURE;
import static javax.security.enterprise.AuthenticationStatus.SUCCESS;
import static javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters.withParams;
import static org.omnifaces.util.Faces.*;
import static org.omnifaces.util.Messages.addGlobalError;


@Model
@Data
@Slf4j
public class LoginController {
    @Inject
    SecurityContext securityContext;

    @Inject
    private LoginStatus loginStatus;

    private String username;
    private String password;
    private boolean remember;

    @NotNull
    @Min(1000)
    @Max(9999)
    private Integer code;

    @Inject
    private TfaGeneratorEJB tfaGeneratorEJB;

    @Inject
    private AccountStoreEJB accountStoreEJB;

    @Inject
    @Param(name = "continue") // Defined in @LoginToContinue of SecurityFormAuthenticationMechanism
    private boolean loginToContinue;

    private void login() throws IOException {
        Credential credential = new UsernamePasswordCredential(username, new Password(password));

        // this will call our security configuration to authorize the user
        AuthenticationStatus status = this.securityContext.authenticate(
                getRequest(),
                getResponse(),
                withParams()
                        .credential(credential)
                        .newAuthentication(!loginToContinue)
                        .rememberMe(remember));

        if (status.equals(SUCCESS)) {
            loginStatus.checkUserAndRoles();
            redirect("viewProducts.xhtml");
        } else if (status.equals(SEND_FAILURE)) {
            addGlobalError("auth failure.");
            validationFailed();
        }
    }

    public void submit() throws IOException {
        Optional<Account> optionalAccount = accountStoreEJB.getByUsername(username);
        if (!optionalAccount.isPresent()) {
            addGlobalError("auth failure..");
            validationFailed();
        }
        Account account = optionalAccount.get();
        if (account.isTfaEnabled()) {
            tfaGeneratorEJB.createCode(username,password,remember);
            redirect("tfa.xhtml");
        } else {
            login();
        }
    }

    public void submitCode() throws IOException {
        if (tfaGeneratorEJB.getCode() == code) {
            username = tfaGeneratorEJB.getUsername();
            password = tfaGeneratorEJB.getPassword();
            remember = tfaGeneratorEJB.isRemember();
            tfaGeneratorEJB.reset();
            log.info("Entered correct security code '{}' for account '{}'",code,username);
            login();
        } else {
            log.info("Entered incorrect security code '{}' for account '{}'",code,tfaGeneratorEJB.getUsername());
            addGlobalError("auth failure...");
            validationFailed();
        }
    }
}
