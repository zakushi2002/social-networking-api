package com.social.networking.api.service;

import com.social.networking.api.utils.AWSCloudUtil;
import com.social.networking.api.view.dto.ApiMessageDto;
import com.social.networking.api.view.dto.UploadFileDto;
import com.social.networking.api.view.dto.aws.FileS3Dto;
import com.social.networking.api.view.form.UploadFileForm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class SocialNetworkingApiService {
    static final String[] UPLOAD_TYPES = new String[]{"LOGO", "AVATAR", "IMAGE", "DOCUMENT"};
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;
    @Value("${cloud.aws.credentials.access.key}")
    private String accessKey;
    @Value("${cloud.aws.credentials.secret.key}")
    private String secretKey;
    @Value("${cloud.aws.s3.endpoint.url}")
    private String endpointUrl;
    private final AWSCloudUtil awsCloudUtil = new AWSCloudUtil();
    @Autowired
    OTPService OTPService;
    @Autowired
    CommonAsyncService commonAsyncService;

    public ApiMessageDto<UploadFileDto> uploadFileS3(UploadFileForm uploadFileForm) {
        ApiMessageDto<UploadFileDto> apiMessageDto = new ApiMessageDto<>();
        try {
            boolean contains = Arrays.stream(UPLOAD_TYPES).anyMatch(uploadFileForm.getType()::equalsIgnoreCase);
            if (!contains) {
                apiMessageDto.setResult(false);
                apiMessageDto.setMessage("Type is required in AVATAR or LOGO");
                return apiMessageDto;
            }
            uploadFileForm.setType(uploadFileForm.getType().toUpperCase());
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(uploadFileForm.getFile().getOriginalFilename()));
            String ext = FilenameUtils.getExtension(fileName);
            String finalFile = uploadFileForm.getType() + "_" + RandomStringUtils.randomAlphanumeric(10) + "." + ext;
            awsCloudUtil.uploadFile(finalFile, uploadFileForm.getFile().getBytes(), accessKey, secretKey, bucketName);
            String fileUrl = endpointUrl + bucketName + "/" + finalFile;
            UploadFileDto uploadFileDto = new UploadFileDto();
            uploadFileDto.setFilePath(fileUrl);
            apiMessageDto.setData(uploadFileDto);
            apiMessageDto.setMessage("Upload file success");
            return apiMessageDto;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
        }
        apiMessageDto.setResult(false);
        apiMessageDto.setMessage("Upload file fail" + uploadFileForm.getFile().getOriginalFilename());
        return apiMessageDto;
    }

    public FileS3Dto loadFileAsResource(String fileName) {
        return awsCloudUtil.downloadFile(fileName, accessKey, secretKey, bucketName);
    }

    public void deleteFileS3(String fileName) {
        awsCloudUtil.deleteFile(fileName, accessKey, secretKey, bucketName);
    }

    public String getOTPForgetPassword() {
        return OTPService.generate(6);
    }

    public void sendEmail(String email, Map<String, Object> variables, String subject) {
        commonAsyncService.sendEmail(email, variables, subject);
    }
}
