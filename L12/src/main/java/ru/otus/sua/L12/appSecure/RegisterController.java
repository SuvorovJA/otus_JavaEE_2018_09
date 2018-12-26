package ru.otus.sua.L12.appSecure;


import lombok.Data;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import static org.omnifaces.util.Messages.addGlobalInfo;

@Model
@Data
public class RegisterController {

    @Inject
    AccountStoreEJB accountStore;
    private String username;
    private String password;
    private String email;

    public void submit() {
        this.accountStore.registerAccount(username, email, password);
        addGlobalInfo("register success");
    }
}
