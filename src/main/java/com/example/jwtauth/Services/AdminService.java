package com.example.jwtauth.Services;

import com.example.jwtauth.Repositories.AdminRepository;
import com.example.jwtauth.Repositories.AttendanceRepository;
import com.example.jwtauth.Repositories.ClassAttendanceRepository;
import com.example.jwtauth.dto.StudentAttendanceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ClassAttendanceRepository classAttendanceRepository;

    @Transactional
    public List<ObjectNode> getAttendance() {
        return adminRepository.getAttendance();
    }
}