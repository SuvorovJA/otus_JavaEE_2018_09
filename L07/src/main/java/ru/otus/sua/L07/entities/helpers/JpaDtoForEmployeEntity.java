package ru.otus.sua.L07.entities.helpers;

import lombok.NoArgsConstructor;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.sua.L07.entities.EmployeEntity;
import ru.otus.sua.L07.entities.Employes;

import javax.persistence.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static ru.otus.sua.L07.entities.helpers.EntityManagerHolder.getEM;
import static ru.otus.sua.L07.entities.helpers.EntityManagerHolder.getFTEM;

@NoArgsConstructor
@SuppressWarnings("Duplicates")
public class JpaDtoForEmployeEntity {

    private static final Logger log = LoggerFactory.getLogger(JpaDtoForEmployeEntity.class);
    private static final EntityManager em = getEM();
    private static final FullTextEntityManager ftem = getFTEM();

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

    // TODO Wildcard search dont work. why?
    public static Employes queryEmployes(String searchFullName,
                                         String searchCity,
                                         String searchDepartament,
                                         String searchAppointment,
                                         String searchLogin) {

        log.info("SearchStrings: " +
                "searchFullName=\'" + searchFullName + "\';" +
                "searchCity=\'" + searchCity + "\';" +
                "searchDepartament=\'" + searchDepartament + "\';" +
                "searchAppointment=\'" + searchAppointment + "\';" +
                "searchLogin=\'" + searchLogin + "\';"
        );

        QueryBuilder queryBuilder =
                ftem.getSearchFactory()
                        .buildQueryBuilder()
                        .forEntity(EmployeEntity.class)
                        .get();

        BooleanQuery query = new BooleanQuery();
        addQuery("fullName", searchFullName, queryBuilder, query);
        addQuery("city", searchCity, queryBuilder, query);
        addQuery("departament", searchDepartament, queryBuilder, query);
        addQuery("appointment", searchAppointment, queryBuilder, query);
        addQuery("login", searchLogin, queryBuilder, query);

        log.info("Query: " + query.toString());

        org.hibernate.search.jpa.FullTextQuery jpaQuery =
                ftem.createFullTextQuery(query, EmployeEntity.class);

        List<EmployeEntity> resultList = jpaQuery.getResultList();
        ArrayList<EmployeEntity> entityArrayList = new ArrayList<>(resultList);
        Employes employes = new Employes();
        employes.setEmployes(entityArrayList);
        return employes;
    }

    private static void addQuery(String field,
                                 String search,
                                 QueryBuilder queryBuilder,
                                 BooleanQuery query) {
        if (search != null && !search.trim().isEmpty()) {
            query.add(queryBuilder
                    .keyword()
                    .onField(field)
                    .matching(search.trim())
                    .createQuery(), BooleanClause.Occur.SHOULD);
        }
    }

    public Employes instanceReadAllEmployesForJSP() throws SQLException {
        return readAllEmployes();
    }

}
