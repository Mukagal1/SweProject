package com.example.jwtauth.Repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class AttendanceRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public String getLastAbsentDate(Long userId, String classId) {
        String queryString = "SELECT a.date FROM Attendance a " +
                "WHERE a.student_id = :userId AND a.class_id = :classId AND a.status = 'absent' " +
                "ORDER BY a.date DESC";
        List<String> dates = entityManager.createQuery(queryString, String.class)
                .setParameter("userId", userId)
                .setParameter("classId", classId)
                .getResultList();
        return dates.isEmpty() ? null : dates.get(0);
    }

    @Transactional
    public Map<String, Long> countByClassId(Long userId, String classId) {
        String statusQueryString = "SELECT DISTINCT a.status FROM Attendance a";
        List<String> allStatuses = entityManager.createQuery(statusQueryString, String.class).getResultList();

        Map<String, Long> statusCounts = new HashMap<>();

        for (String status : allStatuses) {
            String countQueryString = "SELECT COUNT(a) FROM Attendance a " +
                    "WHERE a.student_id = :userId AND a.class_id = :classId AND a.status = :status";
            Long count = entityManager.createQuery(countQueryString, Long.class)
                    .setParameter("userId", userId)
                    .setParameter("classId", classId)
                    .setParameter("status", status)
                    .getSingleResult();
            statusCounts.put(status, count);
        }

        return statusCounts;
    }

    @Transactional
    public String getCourseId(String class_id) {
        String queryString = "SELECT course_id FROM Classes a WHERE a.class_id = :classId";
        return entityManager.createQuery(queryString, String.class)
                .setParameter("classId", class_id)
                .getSingleResult()
                .toString();
    }

    @Transactional
    public Map<String, String> getCourseName(String class_id) {
        String course_id = getCourseId(class_id);
        String queryString = "SELECT coursename FROM Courses a WHERE a.course_id = :course_id";
        String course_name = entityManager.createQuery(queryString, String.class)
                .setParameter("course_id", course_id)
                .getSingleResult()
                .toString();

        Map<String, String> result = new HashMap<>();
        result.put("course_id", course_id);
        result.put("course_name", course_name);

        return result;
    }

    @Transactional
    public ObjectNode getSingleResult(Long userId, String classId) {
        Map<String, String> courseDetails = getCourseName(classId);
        String courseName = courseDetails.get("course_name");
        String courseId = courseDetails.get("course_id");
        Map<String, Long> counts = countByClassId(userId, classId);
        String lastAbsentDate = getLastAbsentDate(userId, classId);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode jsonObject = objectMapper.createObjectNode();

        jsonObject.put("course_id", courseId);
        jsonObject.put("course_name", courseName);
        jsonObject.put("last_absent_date", lastAbsentDate);

        ObjectNode countsObject = objectMapper.createObjectNode();
        for (Map.Entry<String, Long> entry : counts.entrySet()) {
            countsObject.put(entry.getKey(), entry.getValue());
        }
        jsonObject.set("counts", countsObject);

        return jsonObject;
    }

    @Autowired
    private ClassRepository classRepository;

    @Transactional
    public ObjectNode getAttendance(Long userId) {
        List<String> classes = classRepository.getClasses(userId);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode result = objectMapper.createObjectNode();

        for (String classId : classes) {
            ObjectNode classAttendance = getAttendanceForClass(userId, classId);
            result.set(classId, classAttendance);
        }

        return result;
    }

    private ObjectNode getAttendanceForClass(Long userId, String classId) {
        ObjectNode classAttendance = new ObjectMapper().createObjectNode();
        classAttendance.put("class_id", classId);
        classAttendance.set("attendance", getSingleResult(userId, classId));
        return classAttendance;
    }
}
