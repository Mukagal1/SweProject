package com.example.jwtauth.Entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "classes")
public class Classes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_id")
    private String class_id;

    @Column(name = "course_id")
    private String course_id;

    @Column(name = "schedule")
    private String schedule;

    @Column(name = "starttime")
    private String starttime;

    @Column(name = "endtime")
    private String endtime;


    public String getClass_id() {return class_id;}
    public String getCourse_id() {return course_id;}
    public String getSchedule() {return schedule;}
    public String getStarttime() {return starttime;}
    public String getEndtime() {return endtime;}
}