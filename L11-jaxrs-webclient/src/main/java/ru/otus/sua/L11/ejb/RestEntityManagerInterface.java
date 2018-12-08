package ru.otus.sua.L11.ejb;

public interface RestEntityManagerInterface {

    <T> void persist(T entity);

    <T> T merge(T entity);

    <T> void remove(T entity);

    <T> T find(Class<T> clazz, Long id);

}
