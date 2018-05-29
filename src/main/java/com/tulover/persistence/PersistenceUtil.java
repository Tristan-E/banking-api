package com.tulover.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author teyma
 * @since 19/05/2018
 */
public final class PersistenceUtil {

    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;

    public static void initializeEntityManagerFactory() {
        if(entityManagerFactory == null) {
            entityManagerFactory = Persistence.createEntityManagerFactory("bankingApi");
        }
    }

    public static EntityManager getEntityManager(){
        if(entityManager==null){
            if(entityManagerFactory == null) {
                initializeEntityManagerFactory();
            }
            entityManager = entityManagerFactory.createEntityManager();
        }
        return entityManager;
    }

}