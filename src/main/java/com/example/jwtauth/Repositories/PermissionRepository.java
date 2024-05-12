package com.example.jwtauth.Repositories;

import com.example.jwtauth.Entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO Permission (full_name, email, start_date, end_date, file_key, reason) VALUES (:fullName, :email, :startDate, :endDate, :fileKey, :reason)", nativeQuery = true)
    void saveCustom(@Param("fullName") String fullName, @Param("email") String email, @Param("startDate") String startDate, @Param("endDate") String endDate, @Param("fileKey") String fileKey, @Param("reason") String reason);

}