package com.tulover.persistence.repository;

import com.tulover.persistence.AbstractHibernateCRRepository;
import com.tulover.persistence.AbstractHibernateCRURepository;
import com.tulover.persistence.model.Account;
import com.tulover.persistence.model.Movement;

import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * @author teyma
 * @since 21/05/2018
 */
public class MovementRepository extends AbstractHibernateCRRepository<Movement> {

    public MovementRepository(final EntityManager entityManager){
        super(entityManager, Movement.class);
    }
}
