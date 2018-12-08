package ru.otus.sua.L11.addressbookService;

import java.util.List;

public interface RestEntityManagerInterface {

    <T> void persist(T entity);

    <T> T merge(T entity);

    <T> void remove(T entity);

    <T> void remove(Long id);

    <T> T find(Class<T> clazz, Long id);

    <T> List<T> findAll();

    <T> List<T> findRange(int[] range);
}
