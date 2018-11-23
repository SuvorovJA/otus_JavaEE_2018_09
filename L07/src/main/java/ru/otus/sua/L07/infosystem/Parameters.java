package ru.otus.sua.L07.infosystem;

import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.ServletContext;

@ApplicationScoped
@Slf4j
public class Parameters {

    final static String POLL_PERIOD_IN_SECONDS = "extResourcesPollPeriodInSeconds";
    final static int DEFAULT_POLL_PERIOD = 300;

    @Inject
    private ServletContext context;

    public int getPeriod() {

        String stringPeriod = context.getInitParameter(POLL_PERIOD_IN_SECONDS);
        if (stringPeriod == null || stringPeriod.isEmpty()) return DEFAULT_POLL_PERIOD;
        try {
            int period = Integer.parseInt(stringPeriod);
            if (period <= 0) returnDefault();
            return period;
        } catch (NumberFormatException e) {
            return returnDefault();
        }
    }

    private int returnDefault() {
        log.error("Incorrect init parameter: " + POLL_PERIOD_IN_SECONDS + " use default " + DEFAULT_POLL_PERIOD);
        return DEFAULT_POLL_PERIOD;
    }

}
