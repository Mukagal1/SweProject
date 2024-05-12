package com.example.jwtauth.Entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "courses")
public class Courses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private String course_id;

    @Column(name = "coursename")
    private String coursename;

    @Column(name = "teacher_id")
    private Long teacher_id;

    public String getCourse_id() {
        return course_id;
    }

    public String getCoursename() {
        return coursename;
    }

    public Long getTeacher_id() {
        return teacher_id;
    }
}