package com.project.myproject.service;

import org.springframework.web.multipart.MultipartFile;

import java.net.URL;

public interface IStorageService {
    String uploadImageToS3Bucket(MultipartFile multipartFile);
    void deleteImageFromS3Bucket(String keyName);
    URL getUrlFromS3(String keyName);
}
