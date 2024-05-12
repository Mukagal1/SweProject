package com.example.jwtauth.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "attendance")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attendance_id")
    private Long attendence_id;

    @Column(name = "student_id")
    private Long student_id;

    @Column(name = "class_id")
    private String class_id;

    @Column(name = "date")
    private String date;

    @Column(name = "status")
    private String status;

    public String getStatus() {
        return status;
    }
}