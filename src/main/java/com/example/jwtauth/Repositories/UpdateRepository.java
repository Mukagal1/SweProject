package com.example.jwtauth.Repositories;

import com.example.jwtauth.Entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public class UpdateRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void update(Long id, String email, String phoneNumber){
        String jpql = "UPDATE User u SET u.email = :email, u.phoneNumber = :phoneNumber WHERE u.id = :id";
        User user = null;

        try {
            entityManager.createQuery(jpql)
                    .setParameter("id", id)
                    .setParameter("email", email)
                    .setParameter("phoneNumber", phoneNumber)
                    .executeUpdate();
        } catch (NoResultException e) {

        }
    }

    @Transactional
    public User updateUser(Long id, String email, String phoneNumber){
        update(id, email, phoneNumber);
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
