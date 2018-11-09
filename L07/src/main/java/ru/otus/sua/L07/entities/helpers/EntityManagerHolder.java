package ru.otus.sua.L07.entities.helpers;

import org.hibernate.search.MassIndexer;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.sua.L07.entities.EmployeEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.File;
import java.util.Random;

public class EntityManagerHolder {

    private static final Logger log = LoggerFactory.getLogger(EntityManagerHolder.class);

    private static final String PERSISTENCE_UNIT_NAME = "JPAPersistenceUnit7";
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    private static final EntityManager em = emf.createEntityManager();
    private static FullTextEntityManager fullTextEntityManager;
    private static MassIndexer indexer;

    static {
        try {
            fullTextEntityManager = Search.getFullTextEntityManager(em);
            indexer = fullTextEntityManager.createIndexer(EmployeEntity.class);
            indexer.startAndWait();
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
    }

    public static EntityManager getEM() {
        return em;
    }

    public static FullTextEntityManager getFTEM() {
        return fullTextEntityManager;
    }


    // TODO ? err on redeploy. resolve that temporary hack
//Hibernate Search sync consumer thread for index ru.otus.sua.L07.entities.EmployeEntity]
//  ERROR o.h.s.exception.impl.LogErrorHandler - HSEARCH000058: Exception occurred java.nio.channels.OverlappingFileLockException
//Primary Failure:Entity ru.otus.sua.L07.entities.EmployeEntity  Id 3  Work Type
//  org.hibernate.search.backend.AddLuceneWork java.nio.channels.OverlappingFileLockException: null
    public static void shutdownIndexer() {
        fullTextEntityManager.close();

        File dirO = new File("/tmp/data/index/default"); // look persistence.xml
        File dirN = new File("/tmp/data/index/default_" + (new Random()).nextLong());
        dirO.renameTo(dirN);

    }
}
