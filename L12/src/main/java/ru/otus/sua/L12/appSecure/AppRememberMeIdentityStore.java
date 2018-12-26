package ru.otus.sua.L12.appSecure;


import ru.otus.sua.L12.appSecure.entities.Account;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.CallerPrincipal;
import javax.security.enterprise.credential.RememberMeCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.RememberMeIdentityStore;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.Set;

import static javax.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;
import static org.omnifaces.util.Servlets.getRemoteAddr;
import static ru.otus.sua.L12.appSecure.entities.TokenType.REMEMBER_ME;

@ApplicationScoped
public class AppRememberMeIdentityStore implements RememberMeIdentityStore {

    @Inject
    HttpServletRequest request;

    @Inject
    AccountStoreEJB accountStore;

    @Inject
    TokenStoreEJB tokenStore;

    @Override
    public CredentialValidationResult validate(RememberMeCredential rmc) {
        Optional<Account> account = this.accountStore.getByLoginToken(rmc.getToken(), REMEMBER_ME);
        return account.map(a -> new CredentialValidationResult(new CallerPrincipal(a.getUsername()))).orElse(INVALID_RESULT);
    }

    // TODO method not specify said to store the token into database, you only need to return the token and everything is work, but like I said earlier you need to guard the remember me cookie session and then store it on the database which is have been done in EJB TokenStore.
    @Override
    public String generateLoginToken(CallerPrincipal cp, Set<String> set) {
        return this.tokenStore.generate(cp.getName(), getRemoteAddr(request), getDescription(), REMEMBER_ME);
    }

    @Override
    public void removeLoginToken(String loginToken) {
        this.tokenStore.remove(loginToken);
    }

    private String getDescription() {
        return "Remember me session: " + this.request.getHeader("User-Agent");
    }
}
