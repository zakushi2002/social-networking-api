package com.social.networking.api.utils;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.social.networking.api.constant.SocialNetworkingConstant;
import com.social.networking.api.dto.aws.FileS3Dto;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

@Slf4j
public class AWSCloudUtil {
    private AWSCredentials awsCredentials(String accessKey, String secretKey) {
        return new BasicAWSCredentials(accessKey, secretKey);
    }

    private AmazonS3 awsS3ClientBuilder(AWSCredentials credentials) {
        return AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(SocialNetworkingConstant.REGION_STATIC).build();
    }

    public void uploadFile(String fileName, byte[] fileBytes, String accessKey, String secretKey, String bucket) {
        AmazonS3 s3Client = awsS3ClientBuilder(awsCredentials(accessKey, secretKey));
        File file = new File(fileName);
        try (OutputStream os = new FileOutputStream(file)) {
            os.write(fileBytes);
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, fileName, file).withCannedAcl(CannedAccessControlList.PublicReadWrite);
            s3Client.putObject(putObjectRequest);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            file.deleteOnExit();
        }
    }

    public FileS3Dto downloadFile(String fileName, String accessKey, String secretKey, String bucket) {
        AmazonS3 s3Client = awsS3ClientBuilder(awsCredentials(accessKey, secretKey));
        try {
            S3Object s3Object = s3Client.getObject(bucket, fileName);
            FileS3Dto fileS3Dto = new FileS3Dto();
            fileS3Dto.setFileByte(s3Object.getObjectContent().readAllBytes());
            fileS3Dto.setFileType(s3Object.getObjectMetadata().getContentType());
            return fileS3Dto;
        } catch (Exception e) {
            return null;
        }
    }

    public void deleteFile(String fileName, String accessKey, String secretKey, String bucket) {
        AmazonS3 s3Client = awsS3ClientBuilder(awsCredentials(accessKey, secretKey));
        S3Object s3Object = s3Client.getObject(bucket, fileName);
        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucket, s3Object.getKey()).withExpectedBucketOwner("toannguyenit239");
        log.info("Deleting file: " + s3Object.getKey() + " from bucket: " + s3Object.getBucketName());
        s3Client.deleteObject(deleteObjectRequest);
    }
}
