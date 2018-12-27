package ru.otus.sua.L12.appSecure.presentation;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ru.otus.sua.L12.appSecure.AppRoleStoreEJB;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.SecurityContext;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@SessionScoped
@Data
@Named
@Slf4j
@DeclareRoles({ "MANAGER", "ADMIN", "CUSTOMER","REMOTE" })
@PermitAll
public class LoginStatus implements Serializable {

    private String user;

    private String roles;

    @Inject
    private SecurityContext securityContext;

    @Inject
    private AppRoleStoreEJB appRoleStoreEJB;

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

    public void checkUserAndRoles(){

        user = Objects.toString(securityContext.getCallerPrincipal().getName(), "(not logged in...)");

        StringBuilder sb = new StringBuilder(" (assigned roles: ");
        List<String> allRoles = appRoleStoreEJB.findAllAppRolesAsStrings();
        for (String role : allRoles) {
            if (securityContext.isCallerInRole(role)) {
                sb.append(role);
                sb.append(";");
            }
        }
        sb.append(")");
        roles = sb.toString();

        log.info("LoginStatus account: {}, {}", user, roles);
    }

}
