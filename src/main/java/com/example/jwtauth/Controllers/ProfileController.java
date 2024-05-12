package com.example.jwtauth.Controllers;

import com.example.jwtauth.Entities.User;
import com.example.jwtauth.Services.JwtService;
import com.example.jwtauth.dto.UpdateResponse;
import com.example.jwtauth.dto.updateDto;
import com.example.jwtauth.Entities.Users;
import com.example.jwtauth.Services.InfoService;
import com.example.jwtauth.Services.UpdateService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


//@CrossOrigin(origins = "*", allowCredentials = "true")
@RestController
@RequestMapping("/api")
public class ProfileController {

    private final InfoService infoService;
    private final UpdateService updateService;
    private final JwtService jwtService;
    private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);

    public ProfileController(InfoService infoService, UpdateService updateService, JwtService jwtService) {
        this.infoService = infoService;
        this.updateService = updateService;
        this.jwtService = jwtService;
    }

    public Long extractId(String token) {
        return Long.parseLong(jwtService.extractClaim(token, claims -> claims.get("id").toString()));
    }

    @GetMapping("/profile")
    public ResponseEntity<User> showProfile(HttpServletRequest request) {
        String token = jwtService.extractToken(request);
        System.out.println(token);
        Long id = jwtService.extractId(token);
        User user = infoService.information(id);

        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.ok(new User());
        }
    }

    @PostMapping("/profile/update")
    public ResponseEntity<Map<String, Object>> updateProfile(HttpServletRequest request, @RequestBody updateDto updateDto) {
        String token = jwtService.extractToken(request);
        Long id = jwtService.extractId(token);
        User user = updateService.updateUser(id, updateDto.getEmail(), updateDto.getPhoneNumber());
        String newToken = jwtService.generateToken(user.getEmail(), id);
        return ResponseEntity.ok(Map.of("user", user, "token", newToken));
    }
}
