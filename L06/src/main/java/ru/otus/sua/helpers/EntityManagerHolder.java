package ru.otus.sua.helpers;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerHolder {

    private static final String PERSISTENCE_UNIT_NAME = "JPAPersistenceUnit6";
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    private static EntityManager em = emf.createEntityManager();

    public static EntityManager getEM() {
        return em;
    }
}
