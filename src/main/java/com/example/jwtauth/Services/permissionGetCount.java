package com.example.jwtauth.Services;

import com.example.jwtauth.Repositories.permissionCount;
import org.springframework.stereotype.Service;

@Service
public class permissionGetCount {
    private final permissionCount permissionCount;

    public permissionGetCount(permissionCount permissionCount) {
        this.permissionCount = permissionCount;
    }

    public long getPermissionCount() {
        return permissionCount.count();
    }
}