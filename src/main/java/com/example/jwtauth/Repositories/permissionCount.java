package com.example.jwtauth.Repositories;

import com.example.jwtauth.Entities.Permission;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface permissionCount extends CrudRepository<Permission, Long> {
}