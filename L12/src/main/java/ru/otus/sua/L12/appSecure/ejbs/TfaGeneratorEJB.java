package ru.otus.sua.L12.appSecure.ejbs;


import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.Random;

@Singleton
@Slf4j
@Startup
@Getter
public class TfaGeneratorEJB {

    private int code;

    private int getRandom() {
        return new Random().nextInt(9000) + 1000;
    }

    @PostConstruct
    private void init() {
        code = getRandom();
    }

    @Schedule(dayOfWeek = "*", hour = "*", minute = "*/5", second = "0", persistent = false)
    private void change() {
        code = getRandom();
    }
}
