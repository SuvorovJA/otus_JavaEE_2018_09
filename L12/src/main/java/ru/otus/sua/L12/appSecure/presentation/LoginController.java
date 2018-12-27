package ru.otus.sua.L12.appSecure.presentation;

import lombok.Data;
import org.omnifaces.cdi.Param;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.Password;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import java.io.IOException;

import static javax.security.enterprise.AuthenticationStatus.SEND_FAILURE;
import static javax.security.enterprise.AuthenticationStatus.SUCCESS;
import static javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters.withParams;
import static org.omnifaces.util.Faces.*;
import static org.omnifaces.util.Messages.addGlobalError;


@Model
@Data
public class LoginController {
    @Inject
    private LoginStatus loginStatus;
    @Inject
    SecurityContext securityContext;
    private String username;
    private String password;
    private boolean remember;
    @Inject
    @Param(name = "continue") // Defined in @LoginToContinue of SecurityFormAuthenticationMechanism
    private boolean loginToContinue;

    public void submit() throws IOException {
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
            addGlobalError("auth failure");
            validationFailed();
        }
    }

}
