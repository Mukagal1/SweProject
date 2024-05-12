package com.example.jwtauth.Controllers;

import com.example.jwtauth.Services.PermissionService;
import com.example.jwtauth.Services.StorageService;
import com.example.jwtauth.dto.FileDownloadDto;
import com.example.jwtauth.dto.PermissionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/permission")
public class FileController {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private Environment env;

    @Autowired
    private StorageService storageService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFileTest(@ModelAttribute("permissionDto") PermissionDto permissionDto) {
        String fileName = storageService.uploadFile(permissionDto.getFile());
        permissionDto.setFileKey(fileName);
        permissionService.setPermission(permissionDto);
        return new ResponseEntity<>("Uploaded: " + fileName, HttpStatus.OK);
    }
    @PostMapping("/upload/test")
    public ResponseEntity<String> uploadTest(@RequestParam(value = "file") MultipartFile file) {
        String fileName = storageService.uploadFile(file);
        return new ResponseEntity<>(fileName, HttpStatus.OK);
    }

    @GetMapping("/download")
    public ResponseEntity<List<FileDownloadDto>> downloadAllFiles() {
        List<PermissionDto> permissionDtos = permissionService.getAllPermissions();
        List<FileDownloadDto> fileDownloadDtos = new ArrayList<>();

        for (PermissionDto permissionDto : permissionDtos) {
            String fileUrl = storageService.getFileUrl(permissionDto.getFileKey());
            FileDownloadDto fileDownloadDto = new FileDownloadDto();
            fileDownloadDto.setFileKey(permissionDto.getFileKey());
            fileDownloadDto.setFileContent(fileUrl);
            fileDownloadDto.setFullName(permissionDto.getFullName());
            fileDownloadDto.setEndDate(permissionDto.getEndDate());
            fileDownloadDto.setReason(permissionDto.getReason());
            fileDownloadDtos.add(fileDownloadDto);
        }

        Collections.reverse(fileDownloadDtos);

        return ResponseEntity.ok(fileDownloadDtos);
    }
}
