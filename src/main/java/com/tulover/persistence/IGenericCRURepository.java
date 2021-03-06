package com.tulover.persistence;

import java.util.List;

/**
 * Create / Read / Update interface
 * @author teyma
 * @since 20/05/2018
 */
public interface IGenericCRURepository<T> {
    T findOne(final long id);

    List<T> findAll();

    void create(final T entity);

    T update(final T entity);
}


