package com.tulover.persistence;

import java.util.List;

/**
 * @author teyma
 * @since 20/05/2018
 */
public interface IGenericRepository<T> {
    T findOne(final long id);

    List<T> findAll();

    void create(final T entity);

    T update(final T entity);

    void delete(final T entity);

    void deleteById(final long entityId);
}


