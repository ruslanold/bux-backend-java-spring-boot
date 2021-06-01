package com.project.myproject.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class StorageService implements IStorageService{

    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    @Autowired
    private AmazonS3 amazonS3;


    @Override
    public String uploadImageToS3Bucket(MultipartFile multipartFile) {

        if(multipartFile.isEmpty()){
            throw new IllegalStateException("Cannot upload empty file");
        }
        List<String> mimeType = Arrays.asList(
                MediaType.IMAGE_GIF_VALUE,
                MediaType.IMAGE_JPEG_VALUE,
                MediaType.IMAGE_PNG_VALUE
        );
        if(!mimeType.contains(multipartFile.getContentType())){
            throw new IllegalStateException("File uploaded is not an image");
        }

        File fileObj = convertMultipartFileToFile(multipartFile);

        String[] fileType =  multipartFile.getOriginalFilename().split("\\.");
        String imagesFolder = "users/images/";
        String fileName = "1" + "." + fileType[fileType.length - 1];
        String keyName = imagesFolder + fileName;

        PutObjectRequest putObjectRequest = new PutObjectRequest(this.bucketName, keyName, fileObj);
        putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead);
        amazonS3.putObject(putObjectRequest);
        fileObj.deleteOnExit();

        return keyName;
    }


    @Override
    public void deleteImageFromS3Bucket( String keyName) {
        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(this.bucketName, keyName);
        amazonS3.deleteObject(deleteObjectRequest);
    }

    public File convertMultipartFileToFile(MultipartFile multipartFile) {
        File file = new File(multipartFile.getOriginalFilename());

        try(FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(multipartFile.getBytes());
        } catch (IOException e) {
            log.error("Error converting MultipartFile to file", e);
        }

        return file;
    }

    public URL getUrlFromS3(String keyName) {
        return amazonS3.getUrl(this.bucketName, keyName);
    }

}
