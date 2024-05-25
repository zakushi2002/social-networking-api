package com.social.networking.api.service.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.social.networking.api.dto.aws.FileS3Dto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
@Slf4j
public class S3Service {
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Autowired
    private AmazonS3 s3Client;

    public String uploadFile(MultipartFile file, String fileName) {
        File fileObj = convertMultiPartFileToFile(file);
        s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObj));
        boolean delete = fileObj.delete();
        if (!delete) {
            log.error("[AWS S3] Uploaded file failed");
            return "Uploaded file failed";
        }
        log.info("[AWS S3] File uploaded : " + fileName);
        return "File uploaded : " + fileName;
    }

    public FileS3Dto downloadFile(String fileName) {
        S3Object s3Object = s3Client.getObject(bucketName, fileName);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        try {
            byte[] content = IOUtils.toByteArray(inputStream);
            FileS3Dto fileS3Dto = new FileS3Dto();
            fileS3Dto.setFileByte(content);
            fileS3Dto.setFileType(s3Object.getObjectMetadata().getContentType());
            return fileS3Dto;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void deleteFile(String fileName) {
        log.info("[AWS S3] Deleting file: " + fileName + " from bucket: " + bucketName + "...");
        s3Client.deleteObject(bucketName, fileName);
    }


    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            log.error("Error converting multipartFile to file", e);
        }
        return convertedFile;
    }
}
