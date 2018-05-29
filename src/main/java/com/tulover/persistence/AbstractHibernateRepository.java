package com.tulover.persistence;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * @author teyma
 * @since 20/05/2018
 */
public abstract class AbstractHibernateRepository<T> implements IGenericRepository<T> {

    private final EntityManager entityManager;

    private Class<T> clazz;

    public AbstractHibernateRepository(final EntityManager entityManager, Class<T> clazz) {
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

    public T update(T entity) {
        entityManager.getTransaction().begin();
        entity = entityManager.merge(entity);
        entityManager.getTransaction().commit();

        return entity;
    }

    public void delete(T entity) {
        entityManager.getTransaction().begin();
        entityManager.remove(entity);
        entityManager.getTransaction().commit();
    }

    public void deleteById(long entityId) {
        entityManager.getTransaction().begin();

        T entity = (T) entityManager.createQuery(String.format("from %s where id = %d", clazz.getName(), entityId)).getSingleResult();
        if(entity != null) {
            entityManager.remove(entity);
            entityManager.getTransaction().commit();
        } else {
            entityManager.getTransaction().rollback();
        }
    }
}
