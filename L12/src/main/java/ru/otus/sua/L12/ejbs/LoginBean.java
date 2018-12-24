package ru.otus.sua.L12.ejbs;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.security.Principal;

@Named
@SessionScoped
public class LoginBean implements Serializable {

    //    private User user; // The JPA entity.
    private String user;

//    @EJB
//    private UserService userService;

    //    public User getUser() {
    public String getUser() {
        if (user == null) {
            Principal principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
            if (principal != null) {
//                user = userService.find(principal.getName()); // Find User by j_username.
                user = principal.getName();
            }
        }
        return user;
    }

}
