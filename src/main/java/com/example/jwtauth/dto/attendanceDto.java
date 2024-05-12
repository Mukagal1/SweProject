package com.example.jwtauth.dto;

public class attendanceDto {
    private String class_id;
    private String status;

    public attendanceDto(String class_id){
        this.class_id = class_id;
    }

    public attendanceDto(String class_id, String status) {
        this.class_id = class_id;
        this.status = status;
    }

    public String getClass_id() {return class_id;}
    public String getStatus() {return status;}
}