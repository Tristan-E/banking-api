package com.revolut.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author teyma
 * @since 19/05/2018
 */
public class PersistenceUtil {

    private static EntityManager entityManager;

    public static EntityManager getEntityManager(){
        if(entityManager==null){
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("bankingApi");
            entityManager = emf.createEntityManager();
        }
        return entityManager;
    }

}