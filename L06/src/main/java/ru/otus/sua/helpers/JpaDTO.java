package ru.otus.sua.helpers;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.sua.entities.AppointmentEntity;
import ru.otus.sua.entities.DepartmentEntity;
import ru.otus.sua.entities.EmployeEntity;
import ru.otus.sua.entities.Employes;
import ru.otus.sua.shared.entities.MyPair;

import javax.persistence.*;
import java.sql.SQLException;
import java.util.ArrayList;

import static ru.otus.sua.helpers.EntityManagerHolder.getEM;

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

// TODO [WARNING] JpaDTO.java uses unchecked or unsafe operations. Recompile with -Xlint:unchecked for details.

public class JpaDTO {

    private static final Logger log = LoggerFactory.getLogger(JpaDTO.class);
    private static final EntityManager em = getEM();

    public static boolean saveEmployeEntity(EmployeEntity entity) {
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
            log.info("Created in DB {}.hash={}",entity,entity.hashCode());
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
            log.info("Refreshing in DB {}.hash={}",entity,entity.hashCode());
            em.getTransaction().begin();
            em.merge(entity);
            em.flush();
            em.getTransaction().commit();
            log.info("Refreshed in DB {}.hash={}",entity,entity.hashCode());
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

    public static EmployeEntity findByCredentials(MyPair<String, String> cred) throws SQLException {
        // TODO pass as passhash -> to real hash

        EmployeEntity entity = null;
        try {
            em.getTransaction().begin();
            Query q = em.createQuery("select c.employe from CredentialEntity c where c.login = :logi and c.passhash = :pass");
            q.setParameter("logi", cred.getLeft());
            q.setParameter("pass", cred.getRight());
            entity = (EmployeEntity) q.getSingleResult();
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
            log.error("Rollback when findByCredentials: {}", e.getMessage());
            throw new SQLException(e.getMessage());
        }
        if (entity == null) {
            throw new SQLException("Не найдено"); // TODO why not wrk?
        }
        if (entity.getFullname() == null) {
            throw new SQLException("Не найдено.");// TODO why not wrk?
        }
        return entity;
    }


    // AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA

    @SuppressWarnings("Duplicates")
    public static boolean saveAppointmentEntity(AppointmentEntity entity) {
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        } catch (EntityExistsException e) {
            em.getTransaction().rollback();
            log.error("Rollback when saveAppointmentEntity, call update method: {}", e.getMessage());
            updateAppointmentEntity(entity);
            return false;
        } catch (PersistenceException | IllegalArgumentException e) {
            em.getTransaction().rollback();
            log.error("Rollback when saveAppointmentEntity: {}", e.getMessage());
            return false;
        }
        return true;
    }

    @SuppressWarnings("Duplicates")
    public static boolean updateAppointmentEntity(AppointmentEntity entity) {
        try {
            em.getTransaction().begin();
            em.refresh(em.merge(entity));
            em.getTransaction().commit();
        } catch (EntityNotFoundException e) {
            em.getTransaction().rollback();
            log.error("Rollback when updateAppointmentEntity, call saving method: {}", e.getMessage());
            saveAppointmentEntity(entity);
            return false;
        } catch (PersistenceException | IllegalArgumentException e) {
            em.getTransaction().rollback();
            log.error("Rollback when updateAppointmentEntity: {}", e.getMessage());
            return false;
        }
        return true;
    }

    public static boolean containsAppointmentEntity(AppointmentEntity entity) {
        boolean result;
        try {
            em.getTransaction().begin();
            result = em.contains(entity);
            em.getTransaction().commit();
        } catch (PersistenceException | IllegalArgumentException e) {
            em.getTransaction().rollback();
            log.error("Rollback when containsAppointmentEntity: {}", e.getMessage());
            result = false;
        }
        return result;
    }

    public static boolean containsAppointmentEntityByName(String name) {
        boolean result = true;
        Query q = em.createQuery("select a from AppointmentEntity a where a.name=:name");
        q.setParameter("name", name);
        try {
            q.getSingleResult();
        } catch (NoResultException e) {
            log.info("No result \"{}\" in containsAppointmentEntityByName", name);
            result = false;
        } catch (PersistenceException | IllegalArgumentException e) {
            log.error("Err when containsAppointmentEntityByName: {}", e.getMessage());
            result = false;
        }
        return result;
    }

    public static AppointmentEntity readAppointmentEntityByName(String name) {
        AppointmentEntity result = null;
        Query q = em.createQuery("select a from AppointmentEntity a where a.name=:name");
        q.setParameter("name", name);
        try {
            result = (AppointmentEntity) q.getSingleResult();
        } catch (NoResultException e) {
            log.info("No result in readAppointmentEntityByName");
        } catch (PersistenceException | IllegalArgumentException e) {
            log.error("Err when readAppointmentEntityByName: {}", e.getMessage());
        }
        return result;
    }

    // DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD


    @SuppressWarnings("Duplicates")
    public static boolean saveDepartmentEntity(DepartmentEntity entity) {
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

    @SuppressWarnings("Duplicates")
    public static boolean updateDepartmentEntity(DepartmentEntity entity) {
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

    public static boolean containsDepartmentEntity(DepartmentEntity entity) {
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
        Query q = em.createQuery("select d from DepartmentEntity d where d.name=:name");
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

    public static DepartmentEntity readDepartmentEntityByName(String name) {
        DepartmentEntity result = null;
        Query q = em.createQuery("select d from DepartmentEntity d where d.name=:name");
        q.setParameter("name", name);
        try {
            result = (DepartmentEntity) q.getSingleResult();
        } catch (NoResultException e) {
            log.info("No result in readDepartmentEntityByName");
        } catch (PersistenceException | IllegalArgumentException e) {
            log.error("Err when readDepartmentEntityByName: {}", e.getMessage());
        }
        return result;
    }


    // OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO

    /**
     * взять объект из базы по содержимому(@param value) не-id поля.
     * поле должно быть отмечено @NaturalId и @Column(nullable = false, unique = true)
     * Session.class => import org.hibernate.Session;
     *
     * @param tClass - тип Entity (T.class)
     * @param value  - искомая строка
     * @return - объект Entity
     */
    public static <T> T getEntity(Class<T> tClass, String value) {
        return em.unwrap(Session.class).bySimpleNaturalId(tClass).load(value);
    }

}
