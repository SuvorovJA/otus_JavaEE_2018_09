package ru.otus.sua.L12.appSecure.ejbs;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Singleton
public class TokenSchedulerEJB {

    @PersistenceContext
    EntityManager em;

    @Inject
    TokenStoreEJB tokenStore;

    @Schedule(dayOfWeek = "*", hour = "*", minute = "0", second = "0", persistent = false)
    public void hourly() {
        this.tokenStore.removeExpired();
    }
}
