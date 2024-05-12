package com.example.jwtauth.Repositories;

import com.example.jwtauth.Entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public class InfoRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public User findInformation(Long id){
        String jpql = "SELECT u FROM User u WHERE u.id = :id";
        User user = null;

        try {
            user = entityManager.createQuery(jpql, User.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }

        return user;
    }


}
