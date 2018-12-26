package ru.otus.sua.L12.appSecure;

import lombok.Data;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.SecurityContext;
import java.io.Serializable;
import java.util.Objects;

@SessionScoped
@Data
@Named
public class LoginStatus implements Serializable {

    private String user;

    @Inject
    private SecurityContext securityContext;

    @PostConstruct
    private void init() {
        if (securityContext == null) {
            user = "(not logged in.)";
            return;
        }
        if (securityContext.getCallerPrincipal() == null) {
            user = "(not logged in..)";
            return;
        }
        user = Objects.toString(securityContext.getCallerPrincipal().getName(), "(not logged in...)");
    }


}
