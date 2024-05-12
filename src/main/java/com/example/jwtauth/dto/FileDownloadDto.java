package com.example.jwtauth.dto;

import jakarta.annotation.Resource;
import lombok.Data;

import java.io.File;

@Data
public class FileDownloadDto {
    private String fileContent;
    private String fullName;
    private String endDate;
    private String reason;
    private String fileKey;

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }
}
