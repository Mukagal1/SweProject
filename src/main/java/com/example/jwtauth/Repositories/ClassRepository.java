package com.example.jwtauth.Repositories;

import com.example.jwtauth.Entities.User;
import com.example.jwtauth.Entities.Courses;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public class ClassRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public List<String> getClasses(Long userId) {
        String queryString = "SELECT DISTINCT a.class_id FROM Attendance a WHERE a.student_id = :userId";
        return entityManager.createQuery(queryString, String.class)
                .setParameter("userId", userId)
                .getResultList();
    }
}
