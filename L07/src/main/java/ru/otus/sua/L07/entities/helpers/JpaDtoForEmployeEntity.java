package ru.otus.sua.L07.entities.helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.sua.L07.entities.EmployeEntity;
import ru.otus.sua.L07.entities.Employes;

import javax.persistence.*;
import java.sql.SQLException;
import java.util.ArrayList;

import static ru.otus.sua.L07.entities.helpers.EntityManagerHolder.getEM;

@SuppressWarnings("Duplicates")
public class JpaDtoForEmployeEntity {
    private static final Logger log = LoggerFactory.getLogger(JpaDtoForEmployeEntity.class);
    private static final EntityManager em = getEM();

    public static boolean saveEmployeEntity(EmployeEntity entity) {
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
            log.info("Created in DB {}.hash={}", entity, entity.hashCode());
        } catch (EntityExistsException e) {
            em.getTransaction().rollback();
            log.error("Rollback when saveEmployeEntity, call update method: {}", e.getMessage());
            updateEmployeEntity(entity);
            return false;
        } catch (PersistenceException | IllegalArgumentException e) {
            em.getTransaction().rollback();
            log.error("Rollback when updateEmployeEntity: {}", e.getMessage());
            return false;
        }
        return true;
    }

    public static boolean updateEmployeEntity(EmployeEntity entity) {
        try {
            log.info("Refreshing in DB {}.hash={}", entity, entity.hashCode());
            em.getTransaction().begin();
            em.merge(entity);
            em.flush();
            em.getTransaction().commit();
            log.info("Refreshed in DB {}.hash={}", entity, entity.hashCode());
        } catch (EntityNotFoundException e) {
            em.getTransaction().rollback();
            log.error("Rollback when updateEmployeEntity, call saving method: {}", e.getMessage());
            saveEmployeEntity(entity);
            return false;
        } catch (PersistenceException | IllegalArgumentException e) {
            em.getTransaction().rollback();
            log.error("Rollback when updateEmployeEntity: {}", e.getMessage());
            return false;
        }
        return true;
    }

    public static boolean deleteEmployeEntity(EmployeEntity entity) {
        try {
            em.getTransaction().begin();
            // without merge occur "Removing a detached instance"
            // on deleting allow use merge
            em.remove(em.merge(entity));
            em.getTransaction().commit();
        } catch (PersistenceException | IllegalArgumentException e) {
            em.getTransaction().rollback();
            log.error("Rollback when deleteEmployeEntity: {}", e.getMessage());
            return false;
        }
        return true;
    }

    public static EmployeEntity readEmployeById(long id) {
        Query q = em.createQuery("select e from EmployeEntity e where e.id=:id");
        q.setParameter("id", id);
        EmployeEntity result = null;
        try {
            result = (EmployeEntity) q.getSingleResult();
            log.info("readEmployeById({}): {}", id, (result == null) ? "null" : result.toString());
        } catch (PersistenceException e) {
            log.error("Err when readById: {}", e.getMessage());
        }
        return result;
    }

    public static Employes readAllEmployes() throws SQLException {
        Employes list = new Employes();
        try {
            em.getTransaction().begin();
            Query q = em.createQuery("select e from EmployeEntity e order by e.id asc");
            list.setEmployes((ArrayList<EmployeEntity>) q.getResultList());
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
            log.error("Rollback when getAllEmployes: {}", e.getMessage());
        }
        return list;
    }
}
