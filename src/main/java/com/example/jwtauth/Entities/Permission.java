package com.example.jwtauth.Entities;

import jakarta.persistence.*;
import java.util.Arrays;

@Entity
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String full_name;
    private String email;
    private String start_date;
    private String end_date;
    private String fileKey;
    private String reason;

    public Permission() {
    }

    public Permission(String fullName, String email, String startDate, String endDate, String fileKey, String reason) {
        this.full_name = fullName;
        this.email = email;
        this.start_date = startDate;
        this.end_date = endDate;
        this.fileKey = fileKey;
        this.reason = reason;
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return full_name;
    }

    public String getEmail() {
        return email;
    }

    public String getStartDate() {
        return start_date;
    }

    public String getEndDate() {
        return end_date;
    }

    public String getFileKey() {
        return fileKey;
    }

    public String getReason() {
        return reason;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFullName(String fullName) {
        this.full_name = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setStartDate(String startDate) {
        this.start_date = startDate;
    }

    public void setEndDate(String endDate) {
        this.end_date = endDate;
    }

    public void setFileKey(byte[] image) {
        this.fileKey = fileKey;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}