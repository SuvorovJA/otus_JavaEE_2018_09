package ru.otus.sua.L07.entities.helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.sua.L07.entities.DepartamentEntity;

import javax.persistence.*;

import static ru.otus.sua.L07.entities.helpers.EntityManagerHolder.getEM;

/**
 * only CRUD ops.
 * <p>
 * <p>
 * ABOUT .merge in Appointment AND Department (detached objects)
 * <p>
 * When we receive an updated version of an existing simple entity
 * (an entity with no references to other entities) from outside
 * of our application and want to save the new state, we invoke
 * EntityManager.merge to copy that state into the persistence
 * context. Because of the way merging works, we can also do this
 * if we are unsure whether the object has been already persisted.
 * <p>
 * <p>
 * <p>
 * ABOUT .refresh detached aggregated object (em.refresh(em.merge(entity)); - no work)
 * <p>
 * When we need more control over the merging process, we use the "DIY merge pattern."
 * Simple Find obj by id and directly modify it.
 * https://xebia.com/blog/jpa-implementation-patterns-saving-detached-entities/
 */
@SuppressWarnings("Duplicates")
public class DepartamentEntityDAO {
    private static final Logger log = LoggerFactory.getLogger(DepartamentEntityDAO.class);
    private static final EntityManager em = getEM();

    public static boolean saveDepartmentEntity(DepartamentEntity entity) {
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        } catch (EntityExistsException e) {
            em.getTransaction().rollback();
            log.error("Rollback when saveDepartmentEntity, call update method: {}", e.getMessage());
            updateDepartmentEntity(entity);
            return false;
        } catch (PersistenceException | IllegalArgumentException e) {
            em.getTransaction().rollback();
            log.error("Rollback when saveDepartmentEntity: {}", e.getMessage());
            return false;
        }
        return true;
    }

    public static boolean updateDepartmentEntity(DepartamentEntity entity) {
        try {
            em.getTransaction().begin();
            em.refresh(em.merge(entity));
            em.getTransaction().commit();
        } catch (EntityNotFoundException e) {
            em.getTransaction().rollback();
            log.error("Rollback when updateDepartmentEntity, call saving method: {}", e.getMessage());
            saveDepartmentEntity(entity);
            return false;
        } catch (PersistenceException | IllegalArgumentException e) {
            em.getTransaction().rollback();
            log.error("Rollback when updateDepartmentEntity: {}", e.getMessage());
            return false;
        }
        return true;
    }

    public static boolean containsDepartmentEntity(DepartamentEntity entity) {
        boolean result;
        try {
            em.getTransaction().begin();
            result = em.contains(entity);
            em.getTransaction().commit();
        } catch (PersistenceException | IllegalArgumentException e) {
            em.getTransaction().rollback();
            log.error("Rollback when containsDepartmentEntity: {}", e.getMessage());
            result = false;
        }
        return result;
    }

    public static boolean containsDepartmentEntityByName(String name) {
        boolean result = true;
        Query q = em.createQuery("select d from DepartamentEntity d where d.name=:name");
        q.setParameter("name", name);
        try {
            q.getSingleResult();
        } catch (NoResultException e) {
            log.info("No result \"{}\" in containsDepartmentEntityByName", name);
            result = false;
        } catch (PersistenceException | IllegalArgumentException e) {
            log.error("Err when containsDepartmentEntityByName: {}", e.getMessage());
            result = false;
        }
        return result;
    }

    public static DepartamentEntity readDepartmentEntityByName(String name) {
        DepartamentEntity result = null;
        Query q = em.createQuery("select d from DepartamentEntity d where d.name=:name");
        q.setParameter("name", name);
        try {
            result = (DepartamentEntity) q.getSingleResult();
        } catch (NoResultException e) {
            log.info("No result in readDepartmentEntityByName");
        } catch (PersistenceException | IllegalArgumentException e) {
            log.error("Err when readDepartmentEntityByName: {}", e.getMessage());
        }
        return result;
    }
}
