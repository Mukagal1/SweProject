package com.example.jwtauth.Controllers;

import com.example.jwtauth.Services.JwtService;
import com.example.jwtauth.dto.GetClass;
import com.example.jwtauth.Services.AttendanceService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AttendanceController {

    private final JwtService jwtService;

    AttendanceController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Autowired
    private AttendanceService attendanceService;

    @GetMapping("/attendance")
    public ObjectNode getAttendance(HttpServletRequest request) {
        String token = jwtService.extractToken(request);
        Long id = jwtService.extractId(token);
        return attendanceService.getAttendance(id);
    }

    @PostMapping("/attendance/class")
    public List<ObjectNode> getClassAttendance(HttpServletRequest request, @RequestBody GetClass get) {
        System.out.println(get.getClassId());
        String token = jwtService.extractToken(request);
        Long id = jwtService.extractId(token);
        return attendanceService.getClassAttendance(id, get.getClassId());
    }

    public Long extractId(String token) {
        return Long.parseLong(jwtService.extractClaim(token, claims -> claims.get("id").toString()));
    }
}
