package com.tulover.persistence;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Create / Read / Update repository
 * @author teyma
 * @since 20/05/2018
 */
public abstract class AbstractHibernateCRURepository<T> extends AbstractHibernateCRRepository<T> implements IGenericCRURepository<T> {

    public AbstractHibernateCRURepository(final EntityManager entityManager, Class<T> clazz) {
        super(entityManager, clazz);
    }

    public T update(T entity) {
        entityManager.getTransaction().begin();
        entity = entityManager.merge(entity);
        entityManager.getTransaction().commit();

        return entity;
    }
}
