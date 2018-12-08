package ru.otus.sua.L11.addressbookService;

import javax.persistence.EntityManager;
import java.util.List;

public abstract class AbstractFacade<T> {
    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEM();

    public void create(T entity) {
        getEM().persist(entity);
    }

    public void edit(T entity) {
        getEM().merge(entity);
    }

    public void remove(T entity) {
        getEM().remove(getEM().merge(entity));
    }

    public T find(Object id) {
        return getEM().find(entityClass, id);
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEM().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEM().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEM().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEM().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEM().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEM().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEM().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

}
