package com.example.jwtauth.Controllers;


import com.example.jwtauth.Services.AdminService;
import com.example.jwtauth.Services.AttendanceService;
import com.example.jwtauth.Services.JwtService;
import com.example.jwtauth.dto.GetClass;
import com.example.jwtauth.dto.StudentAttendanceDto;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/attendance")
    public List<ObjectNode> getAttendance() {
        return adminService.getAttendance();
    }
}
