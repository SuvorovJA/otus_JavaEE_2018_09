package ru.otus.sua.L12.appSecure;

import org.omnifaces.util.Faces;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.servlet.ServletException;
import java.io.IOException;

import static org.omnifaces.util.Faces.invalidateSession;
import static org.omnifaces.util.Faces.redirect;

@Model
public class LogoutController {

    @Inject
    private LoginStatus loginStatus;

    public void submit() throws ServletException, IOException {
        Faces.logout();
        invalidateSession();
        loginStatus.setUser("(logout)");
        redirect("login.xhtml");
    }
}
