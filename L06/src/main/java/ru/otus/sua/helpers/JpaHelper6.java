package ru.otus.sua.helpers;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.sua.entityes.AppointmentEntity;
import ru.otus.sua.entityes.DepartmentEntity;
import ru.otus.sua.entityes.EmployeEntity;
import ru.otus.sua.entityes.Employes;
import ru.otus.sua.shared.entities.MyPair;

import javax.persistence.*;
import java.sql.SQLException;
import java.util.ArrayList;

import static ru.otus.sua.helpers.EntityManagerClass.getEM;

/**
 * only CRUD ops.
 */

// TODO [WARNING] JpaHelper6.java uses unchecked or unsafe operations. Recompile with -Xlint:unchecked for details.

public class JpaHelper6 {

    private static final Logger log = LoggerFactory.getLogger(JpaHelper6.class);
    private static final EntityManager em = getEM();

    public static boolean saveEmploye(EmployeEntity entity) {
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        } catch (EntityExistsException e) {
            em.getTransaction().rollback();
            log.error("Rollback when saveEmploye, call update method: {}", e.getMessage());
            updateEmploye(entity);
            return false;
        } catch (PersistenceException | IllegalArgumentException e) {
            em.getTransaction().rollback();
            log.error("Rollback when updateEmploye: {}", e.getMessage());
            return false;
        }
        return true;
    }

    public static boolean updateEmploye(EmployeEntity entity) {
        try {
            em.getTransaction().begin();
            em.refresh(entity);
            em.getTransaction().commit();
        } catch (EntityNotFoundException e) {
            em.getTransaction().rollback();
            log.error("Rollback when updateEmploye, call saving method: {}", e.getMessage());
            saveEmploye(entity);
            return false;
        } catch (PersistenceException | IllegalArgumentException e) {
            em.getTransaction().rollback();
            log.error("Rollback when updateEmploye: {}", e.getMessage());
            return false;
        }
        return true;
    }

    public static boolean deleteEmploye(EmployeEntity entity) {
        try {
            em.getTransaction().begin();
            em.remove(entity);
            em.getTransaction().commit();
        } catch (PersistenceException | IllegalArgumentException e) {
            em.getTransaction().rollback();
            log.error("Rollback when deleteEmploye: {}", e.getMessage());
            return false;
        }
        return true;
    }

    public static EmployeEntity readEmployeById(long id) {
        Query q = em.createQuery("select e from EmployeEntity e where e.id=id");
        EmployeEntity result = null;
        try {
            result = (EmployeEntity) q.getSingleResult();
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

    @SuppressWarnings("Duplicates")
    public static boolean saveAppointment(AppointmentEntity entity) {
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        } catch (EntityExistsException e) {
            em.getTransaction().rollback();
            log.error("Rollback when saveAppointment, call update method: {}", e.getMessage());
            updateAppointment(entity);
            return false;
        } catch (PersistenceException | IllegalArgumentException e) {
            em.getTransaction().rollback();
            log.error("Rollback when saveAppointment: {}", e.getMessage());
            return false;
        }
        return true;
    }

    @SuppressWarnings("Duplicates")
    public static boolean updateAppointment(AppointmentEntity entity) {
        try {
            em.getTransaction().begin();
            em.refresh(entity);
            em.getTransaction().commit();
        } catch (EntityNotFoundException e) {
            em.getTransaction().rollback();
            log.error("Rollback when updateAppointment, call saving method: {}", e.getMessage());
            saveAppointment(entity);
            return false;
        } catch (PersistenceException | IllegalArgumentException e) {
            em.getTransaction().rollback();
            log.error("Rollback when updateAppointment: {}", e.getMessage());
            return false;
        }
        return true;
    }

    @SuppressWarnings("Duplicates")
    public static boolean saveDepartment(DepartmentEntity entity) {
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        } catch (EntityExistsException e) {
            em.getTransaction().rollback();
            log.error("Rollback when saveDepartment, call update method: {}", e.getMessage());
            updateDepartment(entity);
            return false;
        } catch (PersistenceException | IllegalArgumentException e) {
            em.getTransaction().rollback();
            log.error("Rollback when saveDepartment: {}", e.getMessage());
            return false;
        }
        return true;
    }

    @SuppressWarnings("Duplicates")
    public static boolean updateDepartment(DepartmentEntity entity) {
        try {
            em.getTransaction().begin();
            em.refresh(entity);
            em.getTransaction().commit();
        } catch (EntityNotFoundException e) {
            em.getTransaction().rollback();
            log.error("Rollback when updateDepartment, call saving method: {}", e.getMessage());
            saveDepartment(entity);
            return false;
        } catch (PersistenceException | IllegalArgumentException e) {
            em.getTransaction().rollback();
            log.error("Rollback when updateDepartment: {}", e.getMessage());
            return false;
        }
        return true;
    }

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
