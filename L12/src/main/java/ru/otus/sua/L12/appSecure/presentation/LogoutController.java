package ru.otus.sua.L12.appSecure.presentation;

import lombok.extern.slf4j.Slf4j;
import org.omnifaces.util.Faces;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.servlet.ServletException;
import java.io.IOException;

import static org.omnifaces.util.Faces.invalidateSession;
import static org.omnifaces.util.Faces.redirect;

@Model
@Slf4j
public class LogoutController {

    @Inject
    private LoginStatus loginStatus;

    public void submit() throws ServletException, IOException {
        log.info("Logout user '{}'.",loginStatus.getUser());
        loginStatus.setUser("(logout)");
        loginStatus.setRoles("");
        loginStatus.checkUserAndRoles();
        Faces.logout();
        invalidateSession();
        redirect("login.xhtml");
    }
}
