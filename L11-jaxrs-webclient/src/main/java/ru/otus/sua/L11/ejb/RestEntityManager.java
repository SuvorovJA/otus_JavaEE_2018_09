package ru.otus.sua.L11.ejb;

import lombok.Data;

import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;


@Data
@ApplicationScoped
public class RestEntityManager implements RestEntityManagerInterface {
    @Override
    public <T> void persist(T entity) {

    }

    @Override
    public <T> T merge(T entity) {
        return null;
    }

    @Override
    public <T> void remove(T entity) {

    }

    @Override
    public <T> T find(Class<T> clazz, Long id) {
        return null;
    }
}
