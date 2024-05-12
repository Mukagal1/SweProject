package com.example.jwtauth.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.jwtauth.Entities.Attendance;

import java.util.List;

@Repository
public interface ClassRep extends JpaRepository<Attendance, Long> {
    @Query("SELECT a.class_id, COUNT(a) FROM Attendance a WHERE a.student_id = :userId GROUP BY a.class_id")
    List<Object[]> getTotalAttendances(@Param("userId") Long userId);

    @Query("SELECT a.class_id, COUNT(a) FROM Attendance a WHERE a.student_id = :userId AND a.status = 'absent' GROUP BY a.class_id")
    List<Object[]> getAbsentAttendances(@Param("userId") Long userId);
}