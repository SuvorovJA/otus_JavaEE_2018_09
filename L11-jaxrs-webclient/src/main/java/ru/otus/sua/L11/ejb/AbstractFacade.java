package ru.otus.sua.L11.ejb;

import java.util.List;

public abstract class AbstractFacade<T> {
    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract RestEntityManager getEntityManager();

    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(entity);
    }

    public T find(Long id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        return null;
    }

    public List<T> findRange(int[] range) {
        return null;
    }

    public int count() {
        return 0;
    }

}
