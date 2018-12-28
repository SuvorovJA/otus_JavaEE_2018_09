package ru.otus.sua.L12.appSecure.ejbs;


import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Random;

@Stateful
@SessionScoped
@Slf4j
@Getter
public class TfaGeneratorEJB implements Serializable {

    private String username;
    private String password;
    private boolean remember;
    private int code;

    private int getRandom() {
        return new Random().nextInt(9000) + 1000;
    }

    public void createCode(String username, String password, boolean remember) {
        this.password = password;
        this.remember = remember;
        this.username = username;
        code = getRandom();
        log.info("Security code for account '{}' is '{}'", username, code);
    }

    public void reset() {
        this.password = "";
        this.remember = false;
        this.username = "";
    }
}
