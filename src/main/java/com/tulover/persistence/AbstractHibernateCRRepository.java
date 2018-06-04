package com.tulover.persistence;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * Create / Read repository
 * @author teyma
 * @since 30/05/2018
 */
public abstract class AbstractHibernateCRRepository<T> {

    @Inject
    protected final EntityManager entityManager;

    protected Class<T> clazz;

    public AbstractHibernateCRRepository(final EntityManager entityManager, Class<T> clazz) {
        this.entityManager = entityManager;
        this.clazz = clazz;
    }

    public T findOne(long id) {
        return (T) entityManager.createQuery(String.format("from %s where id = %d", clazz.getName(), id)).getSingleResult();
    }

    public List<T> findAll() {
        return entityManager.createQuery("from " + clazz.getName()).getResultList();
    }

    public void create(T entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
    }
}
