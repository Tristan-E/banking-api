package com.revolut.persistence;

import java.util.List;

/**
 * @author teyma
 * @since 20/05/2018
 */
public abstract class AbstractHibernateRepository<T> implements IGenericRepository<T>{
    private Class<T> clazz;

    public AbstractHibernateRepository(Class<T> clazz) {
        this.clazz = clazz;
    }

    public T findOne(long id) {
        return null;
    }

    public List<T> findAll() {
        return PersistenceUtil.getEntityManager().createQuery("from " + clazz.getName()).getResultList();
    }

    public void create(T entity) {

    }

    public T update(T entity) {
        return null;
    }

    public void delete(T entity) {

    }

    public void deleteById(long entityId) {

    }
}
