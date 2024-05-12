package com.example.jwtauth.Services;

import com.example.jwtauth.Repositories.AttendanceRepository;
import com.example.jwtauth.Repositories.ClassAttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private ClassAttendanceRepository classAttendanceRepository;

    @Transactional
    public ObjectNode getAttendance(Long userId) {
        return attendanceRepository.getAttendance(userId);
    }

    @Transactional
    public List<ObjectNode> getClassAttendance(Long id, String classId) {
        return classAttendanceRepository.getAttendanceByStudentAndClass(id, classId);
    }

}