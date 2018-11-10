package ru.otus.sua.L07.entities.helpers;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.sua.L07.entities.EmployeEntity;
import ru.otus.sua.L07.entities.validation.MyPair;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.sql.SQLException;

import static ru.otus.sua.L07.entities.helpers.EntityManagerHolder.getEM;


// TODO [WARNING] JpaDTO.java uses unchecked or unsafe operations. Recompile with -Xlint:unchecked for details.

@SuppressWarnings("Duplicates")
public class JpaDTO {

    private static final Logger log = LoggerFactory.getLogger(JpaDTO.class);
    private static final EntityManager em = getEM();

    public static EmployeEntity findByCredentials(MyPair<String, String> cred) throws SQLException {
        // TODO pass as passhash -> to real hash

        EmployeEntity entity;
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
        if (entity.getFullName() == null) {
            throw new SQLException("Не найдено.");// TODO why not wrk?
        }
        return entity;
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
