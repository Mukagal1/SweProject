package com.example.jwtauth.dto;

import com.example.jwtauth.Entities.User;

public class UpdateResponse {
    private User user;
    private String claimedPassword;

    public UpdateResponse(User user, String claimedPassword) {
        this.user = user;
        this.claimedPassword = claimedPassword;
    }
}
