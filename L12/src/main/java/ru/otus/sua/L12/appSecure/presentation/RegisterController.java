package ru.otus.sua.L12.appSecure.presentation;


import lombok.Data;
import ru.otus.sua.L12.appSecure.AccountStoreEJB;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import java.util.Objects;

import static org.omnifaces.util.Messages.addGlobalInfo;

@Model
@Data
public class RegisterController {

    @Inject
    AccountStoreEJB accountStore;
    private String username;
    private String password;
    private String email;
    private String selectedrole;

    public void submit() {
        this.accountStore.registerAccount(username, email, password,
                Objects.toString(selectedrole,"CUSTOMER"));
        addGlobalInfo("register success");
    }
}
