package com.example.jwtauth.Repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ClassAttendanceRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ObjectMapper objectMapper;

    @Transactional
    public List<ObjectNode> getAttendanceByStudentAndClass(Long studentId, String classId) {
        String queryString = "SELECT a.date, a.status FROM Attendance a WHERE a.student_id = :studentId AND a.class_id = :classId";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("studentId", studentId);
        query.setParameter("classId", classId);

        List<ObjectNode> resultList = new ArrayList<>();
        List<Object[]> queryResultList = query.getResultList();

        for (Object[] result : queryResultList) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("date", (String) result[0]);
            objectNode.put("status", (String) result[1]);
            resultList.add(objectNode);
        }

        return resultList;
    }
}

