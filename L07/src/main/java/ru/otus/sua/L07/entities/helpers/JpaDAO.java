package ru.otus.sua.L07.entities.helpers;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;

import static ru.otus.sua.L07.entities.helpers.EntityManagerHolder.getEM;


public class JpaDAO {

    private static final Logger log = LoggerFactory.getLogger(JpaDAO.class);
    private static final EntityManager em = getEM();

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
