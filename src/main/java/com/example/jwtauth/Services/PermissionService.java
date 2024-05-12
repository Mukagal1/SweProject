package com.example.jwtauth.Services;

import com.example.jwtauth.Entities.Permission;
import com.example.jwtauth.dto.PermissionDto;
import com.example.jwtauth.Repositories.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    public PermissionDto setPermission(PermissionDto permissionDto) {
        permissionRepository.saveCustom(permissionDto.getFullName(), permissionDto.getEmail(), permissionDto.getStartDate(), permissionDto.getEndDate(), permissionDto.getFileKey(), permissionDto.getReason());
        return permissionDto;
    }

    public List<PermissionDto> getAllPermissions() {
        List<Permission> permissions = permissionRepository.findAll();
        return permissions.stream().map(permission -> {
            PermissionDto permissionDto = new PermissionDto();
            permissionDto.setFullName(permission.getFullName());
            permissionDto.setEmail(permission.getEmail());
            permissionDto.setStartDate(permission.getStartDate());
            permissionDto.setEndDate(permission.getEndDate());
            permissionDto.setFileKey(permission.getFileKey());
            permissionDto.setReason(permission.getReason());
            return permissionDto;
        }).collect(Collectors.toList());
    }
}