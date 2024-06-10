package com.social.networking.api.service;

import com.social.networking.api.constant.SocialNetworkingConstant;
import com.social.networking.api.service.aws.S3Service;
import com.social.networking.api.dto.ApiMessageDto;
import com.social.networking.api.dto.UploadFileDto;
import com.social.networking.api.dto.aws.FileS3Dto;
import com.social.networking.api.form.UploadFileForm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class SocialNetworkingApiService {
    static final String[] UPLOAD_TYPES = new String[]{"LOGO", "AVATAR", "IMAGE", "DOCUMENT"};
    @Value("${cloud.aws.s3.endpoint.url}")
    private String endpointUrl;
    @Autowired
    private S3Service s3Service;
    @Autowired
    OTPService OTPService;
    @Autowired
    CommonAsyncService commonAsyncService;

    public ApiMessageDto<UploadFileDto> uploadFileS3(UploadFileForm uploadFileForm) {
        ApiMessageDto<UploadFileDto> apiMessageDto = new ApiMessageDto<>();
        boolean contains = Arrays.stream(UPLOAD_TYPES).anyMatch(uploadFileForm.getType()::equalsIgnoreCase);
        if (!contains) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("[AWS S3] Type is required in AVATAR or LOGO");
            return apiMessageDto;
        }
        uploadFileForm.setType(uploadFileForm.getType().toUpperCase());
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(uploadFileForm.getFile().getOriginalFilename()));
        String ext = FilenameUtils.getExtension(fileName);
        String finalFile = uploadFileForm.getType() + "_" + RandomStringUtils.randomAlphanumeric(10) + "." + ext;
        String fileUrl = endpointUrl + finalFile;
        UploadFileDto uploadFileDto = new UploadFileDto();
        uploadFileDto.setFilePath(fileUrl);
        apiMessageDto.setData(uploadFileDto);
        apiMessageDto.setMessage(s3Service.uploadFile(uploadFileForm.getFile(), finalFile));
        return apiMessageDto;
    }

    public FileS3Dto loadFileAsResource(String fileName) {
        return s3Service.downloadFile(fileName);
    }

    public void deleteFileS3ByLink(String avatarPath) {
        String awsEndpoint = endpointUrl;
        if (!avatarPath.contains(awsEndpoint)) {
            log.error("[AWS S3] File not found!");
            return;
        }
        String key = avatarPath.replace(awsEndpoint, "");
        s3Service.deleteFile(key);
    }
    public void deleteFileS3(String avatarPath) {
        s3Service.deleteFile(avatarPath);
    }

    public String getOTPForgetPassword() {
        return OTPService.generate(SocialNetworkingConstant.OTP_LENGTH);
    }

    public void sendEmail(String email, Map<String, Object> variables, String subject) {
        commonAsyncService.sendEmail(email, variables, subject);
    }
}
