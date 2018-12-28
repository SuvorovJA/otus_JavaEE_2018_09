package ru.otus.sua.L12.appSecure.presentation;

import lombok.Data;
import ru.otus.sua.L12.appSecure.ejbs.AppRoleStoreEJB;

import javax.ejb.EJB;
import javax.enterprise.inject.Model;

@Model
@Data
public class AppRoleController {

    @EJB
    private AppRoleStoreEJB appRoleStoreEJB;

    private String rolename;

    public void doCreateRole() {
        appRoleStoreEJB.addAppRole(rolename);
    }
}
