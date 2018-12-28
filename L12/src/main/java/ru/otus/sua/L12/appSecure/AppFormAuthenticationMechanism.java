package ru.otus.sua.L12.appSecure;

import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.authentication.mechanism.http.*;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.identitystore.IdentityStoreHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@AutoApplySession // TODO read about this
@RememberMe(
        cookieMaxAgeSeconds = 60 * 60 * 24 * 14, // 14 days
        cookieSecureOnly = false, // TODO Remove this when login is served over HTTPS.
        isRememberMeExpression = "#{self.isRememberMe(httpMessageContext)}"
)
@LoginToContinue(
        loginPage = "/login.xhtml?continue=true",
        errorPage = "/loginError.xhtml",
        useForwardToLogin = false
)
@ApplicationScoped
@Slf4j
public class AppFormAuthenticationMechanism implements HttpAuthenticationMechanism {

    @Inject
    IdentityStoreHandler identityStoreHandler;

    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest req, HttpServletResponse res, HttpMessageContext context) {
        Credential credential = context.getAuthParameters().getCredential();
        if (credential != null) {
            log.info("Credential {}", credential);
            return context.notifyContainerAboutLogin(this.identityStoreHandler.validate(credential));
        } else {
            return context.doNothing();
        }
    }

    // this was called on @RememberMe annotations
    public Boolean isRememberMe(HttpMessageContext httpMessageContext) {
        return httpMessageContext.getAuthParameters().isRememberMe();
    }

    // Workaround for Weld bug; at least in Weld 2.3.2 default methods are not intercepted
//    @Override
//    public void cleanSubject(HttpServletRequest request, HttpServletResponse response, HttpMessageContext httpMessageContext) {
//        HttpAuthenticationMechanism.super.cleanSubject(request, response, httpMessageContext);
//    }
}

