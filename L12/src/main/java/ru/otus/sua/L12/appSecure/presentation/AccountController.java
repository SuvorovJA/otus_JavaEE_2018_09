package ru.otus.sua.L12.appSecure.presentation;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ru.otus.sua.L12.appSecure.ejbs.AccountStoreEJB;
import ru.otus.sua.L12.appSecure.ejbs.AppRoleStoreEJB;
import ru.otus.sua.L12.appSecure.entities.Account;
import ru.otus.sua.L12.appSecure.entities.Role;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.Objects;

@Named
@RequestScoped
@Data
@Slf4j
public class AccountController {

    @EJB
    private AccountStoreEJB accountStoreEJB;

    @EJB
    private AppRoleStoreEJB appRoleStoreEJB;

    private Account account;

    private String selectedrole;

    private boolean tfaEnabled;

    @PostConstruct
    private void init() {
        account = new Account();
    }

    public void doFindAccountById() {
        log.info("Account id={} for modification.", account.getId());
        account = accountStoreEJB.findAccountById(account.getId());
        tfaEnabled = account.isTfaEnabled();
    }

    public void doFindAccountById(long id) {
        log.info("Account id={} for modification.", id);
        account = accountStoreEJB.findAccountById(id);
        tfaEnabled = account.isTfaEnabled();
    }

    public String doAddRoleToAccount() {
        if (selectedrole == null) return "usersAndRoles.xhtml";
        Role role = new Role();
        role.setRole(selectedrole);
        accountStoreEJB.updateAccountRoles(role,account.getId());
        return "usersAndRoles.xhtml";
    }

    public String doModifyTfaToAccount() {
        accountStoreEJB.updateAccountTfa(tfaEnabled,account.getId());
        return "usersAndRoles.xhtml";
    }
}
