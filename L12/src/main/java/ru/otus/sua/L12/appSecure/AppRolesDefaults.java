package ru.otus.sua.L12.appSecure;

import ru.otus.sua.L12.appSecure.ejbs.AppRoleStoreEJB;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class AppRolesDefaults {

    @EJB
    private AppRoleStoreEJB appRoleStoreEJB;

    @PostConstruct
    private void init(){
        if (appRoleStoreEJB.findAllAppRoles().size() == 0){
            appRoleStoreEJB.addAppRole("CUSTOMER");
            appRoleStoreEJB.addAppRole("MANAGER");
            appRoleStoreEJB.addAppRole("ADMIN");
            appRoleStoreEJB.addAppRole("REMOTE");
        }
    }
}
