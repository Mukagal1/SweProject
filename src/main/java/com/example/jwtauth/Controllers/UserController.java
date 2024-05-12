package com.example.jwtauth.Controllers;

import com.example.jwtauth.Entities.Users;
import com.example.jwtauth.Services.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import com.example.jwtauth.Entities.AuthRequest;
import com.example.jwtauth.Entities.User;

import java.util.Map;

@RestController
@RequestMapping("/auth")
//@Component
public class UserController {
    private final UserInfoService service;
    private final JwtService jwtService;
    private final InfoService infoService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final com.example.jwtauth.Services.permissionGetCount permissionGetCount;
    private final UserService userService;

    UserController(UserInfoService service, JwtService jwtService, InfoService infoService, AuthenticationManager authenticationManager, @Qualifier("userDetailsService") UserDetailsService userDetailsService, PasswordEncoder passwordEncoder, permissionGetCount permissionGetCount, UserService userService) {
        this.service = service;
        this.jwtService = jwtService;
        this.infoService = infoService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.permissionGetCount = permissionGetCount;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());
        if (userDetails != null) {
            if (isPasswordHashed(authRequest.getPassword()) || passwordEncoder.matches(authRequest.getPassword(), userDetails.getPassword())) {
                Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
                if (authentication.isAuthenticated()) {
                    UserInfoDetails userInfoDetails = (UserInfoDetails) authentication.getPrincipal();
                    Long id = userInfoDetails.getId();
                    String token = jwtService.generateToken(authRequest.getEmail(), id);
                    User user = infoService.information(id);
                    Long count = 0L;
                    if (user.getUserRole().equals("admin")){
                        count = permissionGetCount.getPermissionCount();
                    } else {
                        count = (long) userService.getClassesWithHighAbsentStatus(user.getId());
                    }
                    System.out.println("COUNT: " + count);
                    return ResponseEntity.ok().body(Map.of("success", true, "message", "Login successful.", "token", token, "count", count, "user", user));
                }
            } else {
                String hashedPassword = passwordEncoder.encode(authRequest.getPassword());
                service.updateUserPassword(authRequest.getEmail(), hashedPassword);
                userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());
                Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), hashedPassword));
                if (authentication.isAuthenticated()) {
                    UserInfoDetails userInfoDetails = (UserInfoDetails) authentication.getPrincipal();
                    Long id = userInfoDetails.getId();
                    String token = jwtService.generateToken(authRequest.getEmail(), id);
                    User user = infoService.information(id);
                    Long count = 0L;
                    if (user.getUserRole().equals("admin")){
                        count = permissionGetCount.getPermissionCount();
                    } else {
                        count = (long) userService.getClassesWithHighAbsentStatus(user.getId());
                    }
                    System.out.println("COUNT: " + count);
                    return ResponseEntity.ok().body(Map.of("success", true, "message", "Login successful.", "token", token, "count", count,"user", user));
                }
            }
        }
        throw new UsernameNotFoundException("Invalid email or password!");
    }

    private boolean isPasswordHashed(String password) {
        return password.startsWith("$2a$");
    }

    public Long extractId(String token) {
        return Long.parseLong(jwtService.extractClaim(token, claims -> claims.get("id").toString()));
    }

    @GetMapping("/hello")
    public String hello(HttpServletRequest request) {
        String token = jwtService.extractToken(request);
        Long id = jwtService.extractId(token);
        return "Hello " + id;
    }
}