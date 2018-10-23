package ru.otus.sua.helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.sua.entityes.AppointmentEntity;
import ru.otus.sua.entityes.DepartmentEntity;
import ru.otus.sua.entityes.EmployeEntity;
import ru.otus.sua.entityes.Employes;

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
        Employes list = null;
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
}
