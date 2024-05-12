package com.example.jwtauth.Repositories;

import com.example.jwtauth.Entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
@Component
public interface UserInfoRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);
}