package ru.otus.sua.statistic;

import org.hibernate.search.MassIndexer;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.File;
import java.util.Random;


public class EntityManagerHolder {

    private static final Logger log = LoggerFactory.getLogger(EntityManagerHolder.class);

    private static final String PERSISTENCE_UNIT_NAME = "JPAPersistenceUnit8";
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    private static final EntityManager em = emf.createEntityManager();
    private static FullTextEntityManager fullTextEntityManager;
    private static MassIndexer indexer;

    static {
        try {
            fullTextEntityManager = Search.getFullTextEntityManager(em);
            indexer = fullTextEntityManager.createIndexer(StatisticEntity.class);
            indexer.startAndWait();
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        } finally {
            fullTextEntityManager.flushToIndexes();
        }
    }

    public static EntityManager getEM() {
        return em;
    }

    public static FullTextEntityManager getFTEM() {
        return fullTextEntityManager;
    }

    // TODO ? err on redeploy with second indexer in App. unresolved
//    17-Nov-2018 02:17:15.268 WARNING [RMI TCP Connection(7)-127.0.0.1]
// org.apache.catalina.loader.WebappClassLoaderBase.clearReferencesThreads
// The web application [L08] appears to have started a thread named [
//    Hibernate Search sync consumer thread for index ru.otus.sua.statistic.StatisticEntity] but has failed to stop it.
// This is very likely to create a memory leak. Stack trace of thread:
// sun.misc.Unsafe.park( Native Method)
// java.util.concurrent.locks.LockSupport.parkNanos(LockSupport.java:338)
// org.hibernate.search.backend.impl.lucene.SyncWorkProcessor.parkCurrentThread(SyncWorkProcessor.java:179)
// org.hibernate.search.backend.impl.lucene.SyncWorkProcessor.access$300(SyncWorkProcessor.java:37)
// org.hibernate.search.backend.impl.lucene.SyncWorkProcessor$Consumer.run(SyncWorkProcessor.java:149)
// java.lang.Thread.run(Thread.java:748)

    // TODO ? err on redeploy. resolve that temporary hack
//Hibernate Search sync consumer thread for index ru.otus.sua.L07.entities.EmployeEntity]
//  ERROR o.h.s.exception.impl.LogErrorHandler - HSEARCH000058: Exception occurred java.nio.channels.OverlappingFileLockException
//Primary Failure:Entity ru.otus.sua.L07.entities.EmployeEntity  Id 3  Work Type
//  org.hibernate.search.backend.AddLuceneWork java.nio.channels.OverlappingFileLockException: null
    public static void shutdownIndexer() {
        fullTextEntityManager.flushToIndexes();
        fullTextEntityManager.close();

        String indexFolderName = emf.getProperties().get("hibernate.search.default.indexBase").toString();
        File dirO = new File(indexFolderName); // look persistence.xml
        File dirN = new File(indexFolderName + "_" + (new Random()).nextLong());
        dirO.renameTo(dirN);

    }
}
