package com.example.jwtauth.Services;

import com.example.jwtauth.Entities.User;
import com.example.jwtauth.Repositories.InfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpSession;

@Service
public class InfoService {

    @Autowired
    private InfoRepository infoRepository;

    public User information(Long userId) {
        return infoRepository.findInformation(userId);
    }
}
