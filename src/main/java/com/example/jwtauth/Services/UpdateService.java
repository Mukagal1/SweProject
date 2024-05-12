package com.example.jwtauth.Services;

import com.example.jwtauth.Entities.User;
import com.example.jwtauth.Repositories.UpdateRepository;
import com.example.jwtauth.dto.UpdateResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateService {

    @Autowired
    private UpdateRepository updateRepository;

    public User updateUser(Long id, String email, String phoneNumber) {
        User user = updateRepository.updateUser(id, email, phoneNumber);
        if (user != null) {
            return user;
        } else {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
    }
}
