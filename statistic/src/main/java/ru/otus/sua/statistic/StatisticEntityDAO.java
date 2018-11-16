package ru.otus.sua.statistic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;

import static ru.otus.sua.statistic.EntityManagerHolder.getEM;

public class StatisticEntityDAO {


    private static final Logger log = LoggerFactory.getLogger(StatisticEntityDAO.class);
    private static final EntityManager em = getEM();

    public static boolean saveStatisticEntity(StatisticEntity entity) {
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        } catch (EntityExistsException e) {
            em.getTransaction().rollback();
            log.error("Rollback when saveStatisticEntity, call update method: {}", e.getMessage());
            updateStatisticEntity(entity);
            return false;
        } catch (PersistenceException | IllegalArgumentException e) {
            em.getTransaction().rollback();
            log.error("Rollback when saveStatisticEntity: {}", e.getMessage());
            return false;
        }
        return true;
    }

    public static long saveStatisticEntityWithIdReturn(StatisticEntity entity) {
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        } catch (EntityExistsException e) {
            em.getTransaction().rollback();
            log.error("Rollback when saveStatisticEntity, call update method: {}", e.getMessage());
            updateStatisticEntity(entity);
            return 0;
        } catch (PersistenceException | IllegalArgumentException e) {
            em.getTransaction().rollback();
            log.error("Rollback when saveStatisticEntity: {}", e.getMessage());
            return 0;
        }
        return entity.getId();
    }

    public static boolean updateStatisticEntity(StatisticEntity entity) {
        try {
            em.getTransaction().begin();
            em.refresh(em.merge(entity));
            em.getTransaction().commit();
        } catch (EntityNotFoundException e) {
            em.getTransaction().rollback();
            log.error("Rollback when updateStatisticEntity, call saving method: {}", e.getMessage());
            saveStatisticEntity(entity);
            return false;
        } catch (PersistenceException | IllegalArgumentException e) {
            em.getTransaction().rollback();
            log.error("Rollback when updateStatisticEntity: {}", e.getMessage());
            return false;
        }
        return true;
    }

 public static boolean containsStatisticEntity(StatisticEntity entity) {
        boolean result;
        try {
            em.getTransaction().begin();
            result = em.contains(entity);
            em.getTransaction().commit();
        } catch (PersistenceException | IllegalArgumentException e) {
            em.getTransaction().rollback();
            log.error("Rollback when containsStatisticEntity: {}", e.getMessage());
            result = false;
        }
        return result;
    }

    public static StatisticEntity readStatisticEntityById(long id) {
        StatisticEntity result = null;
        Query q = em.createQuery("select a from StatisticEntity a where a.id=:id");
        q.setParameter("id", id);
        try {
            result = (StatisticEntity) q.getSingleResult();
        } catch (NoResultException e) {
            log.info("No result in readStatisticEntityById");
        } catch (PersistenceException | IllegalArgumentException e) {
            log.error("Err when readStatisticEntityById: {}", e.getMessage());
        }
        return result;
    }

}
