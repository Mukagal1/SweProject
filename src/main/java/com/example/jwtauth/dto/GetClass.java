package com.example.jwtauth.dto;

public class GetClass {
    private String classId;

    public GetClass() {
    }

    public GetClass(String classId) {
        this.classId = classId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }
}
