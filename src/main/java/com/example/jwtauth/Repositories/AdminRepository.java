package com.example.jwtauth.Repositories;

import com.example.jwtauth.dto.StudentAttendanceDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class AdminRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public List<Object[]> getAllStudents() {
        String queryString = "SELECT u.id, u.fullName FROM User u WHERE u.userRole = 'student'";
        return entityManager.createQuery(queryString).getResultList();
    }

    @Transactional
    public Map<String, Long> getAbsentCounts(Long userId) {
        String queryString = "SELECT c.class_id, COUNT(a) FROM Classes c LEFT JOIN Attendance a ON a.class_id = c.class_id AND a.student_id = :userId AND a.status = 'absent' GROUP BY c.class_id";
        List<Object[]> results = entityManager.createQuery(queryString).setParameter("userId", userId).getResultList();

        Map<String, Long> absentCounts = new HashMap<>();
        for (Object[] result : results) {
            absentCounts.put((String) result[0], result[1] != null ? (Long) result[1] : 0);
        }
        return absentCounts;
    }

    @Transactional
    public String getCourseId(String classId) {
        String queryString = "SELECT c.course_id FROM Classes c WHERE c.class_id = :classId";
        return entityManager.createQuery(queryString, String.class)
                .setParameter("classId", classId)
                .getSingleResult();
    }

    @Transactional
    public String getCourseName(String courseId) {
        String queryString = "SELECT c.coursename FROM Courses c WHERE c.course_id = :courseId";
        return entityManager.createQuery(queryString, String.class)
                .setParameter("courseId", courseId)
                .getSingleResult();
    }

    @Transactional
    public List<ObjectNode> getAttendance() {
        List<Object[]> students = getAllStudents();
        List<ObjectNode> attendanceList = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();

        for (Object[] student : students) {
            Long userId = (Long) student[0];
            String fullName = (String) student[1];

            Map<String, Long> absentCounts = getAbsentCounts(userId);

            ObjectNode studentNode = objectMapper.createObjectNode();
            studentNode.put("studentId", userId);
            studentNode.put("studentFullName", fullName);

            ArrayNode classAttendanceArray = objectMapper.createArrayNode();

            for (Map.Entry<String, Long> entry : absentCounts.entrySet()) {
                String classId = entry.getKey();
                Long absentCount = entry.getValue();

                String courseId = getCourseId(classId);
                String courseName = getCourseName(courseId);

                ObjectNode classAttendance = objectMapper.createObjectNode();
                classAttendance.put("absentCount", absentCount);
                classAttendance.put("courseId", courseId);
                classAttendance.put("courseName", courseName);

                classAttendanceArray.add(classAttendance);
            }

            studentNode.set("classes", classAttendanceArray);

            attendanceList.add(studentNode);
        }

        return attendanceList;
    }
}

