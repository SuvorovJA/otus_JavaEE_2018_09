package ru.otus.sua.L12.appSecure.presentation;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ru.otus.sua.L12.appSecure.AccountStoreEJB;
import ru.otus.sua.L12.appSecure.AppRoleStoreEJB;
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

    @PostConstruct
    private void init() {
        account = new Account();
    }

    public void doFindAccountById() {
        log.info("Account id={} for modification.",account.getId());
        account = accountStoreEJB.findAccountById(account.getId());
    }

    public void doFindAccountById(long id) {
        log.info("Account id={} for modification.",id);
        account = accountStoreEJB.findAccountById(id);
    }

    public String doAddRoleToAccount() {
        log.info("Selected role={} for account modification.",
                Objects.toString(selectedrole,"null-role"));
        if (selectedrole == null) return "usersAndRoles.xhtml";
        doFindAccountById(account.getId());
        Role role = new Role();
        role.setRole(selectedrole);
        account.addRole(role);
        log.info("Account roleset for save: {}",account.getRolesAsStrings());
        accountStoreEJB.updateAccountRoles(account.getRoles(),account);
        log.info("Account={}, Account roleset={}",account,account.getRolesAsStrings());
        return "usersAndRoles.xhtml";
    }
}
