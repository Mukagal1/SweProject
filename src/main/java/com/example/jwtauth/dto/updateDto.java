package com.example.jwtauth.dto;

import jakarta.persistence.Column;

public class updateDto {
    private String email;
    private String phoneNumber;

    public updateDto() {
    }

    public updateDto(String email, String password, String phoneNumber) {
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {return phoneNumber;}

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
