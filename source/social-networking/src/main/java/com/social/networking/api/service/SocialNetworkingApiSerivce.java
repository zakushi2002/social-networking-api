package com.social.networking.api.service;

import com.social.networking.api.constant.SocialNetworkingConstant;
import com.social.networking.api.view.dto.ApiMessageDto;
import com.social.networking.api.view.dto.UploadFileDto;
import com.social.networking.api.view.form.UploadFileForm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

@Service
@Slf4j
public class SocialNetworkingApiSerivce {
    static final String[] UPLOAD_TYPES = new String[]{"LOGO", "AVATAR", "IMAGE", "DOCUMENT"};
    @Autowired
    RestTemplate restTemplate;

    public ApiMessageDto<UploadFileDto> storeFile(UploadFileForm uploadFileForm) {
        // Normalize file name
        ApiMessageDto<UploadFileDto> apiMessageDto = new ApiMessageDto<>();
        try {
            boolean contains = Arrays.stream(UPLOAD_TYPES).anyMatch(uploadFileForm.getType()::equalsIgnoreCase);
            if (!contains) {
                apiMessageDto.setResult(false);
                apiMessageDto.setMessage("Type is required in AVATAR or LOGO");
                return apiMessageDto;
            }
            uploadFileForm.setType(uploadFileForm.getType().toUpperCase());
            String fileName = StringUtils.cleanPath(uploadFileForm.getFile().getOriginalFilename());
            String ext = FilenameUtils.getExtension(fileName);
            //upload to uploadFolder/TYPE/id
            String finalFile = uploadFileForm.getType() + "_" + RandomStringUtils.randomAlphanumeric(10) + "." + ext;
            String typeFolder = File.separator + uploadFileForm.getType();

            Path fileStorageLocation = Paths.get(SocialNetworkingConstant.ROOT_DIRECTORY + typeFolder).toAbsolutePath().normalize();
            Files.createDirectories(fileStorageLocation);
            Path targetLocation = fileStorageLocation.resolve(finalFile);
            Files.copy(uploadFileForm.getFile().getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            UploadFileDto uploadFileDto = new UploadFileDto();
            uploadFileDto.setFilePath(typeFolder + File.separator + finalFile);
            apiMessageDto.setData(uploadFileDto);
            apiMessageDto.setMessage("Upload file success");
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
        }
        return apiMessageDto;
    }

    public Resource loadFileAsResource(String folder, String fileName) {
        try {
            Path fileStorageLocation = Paths.get(SocialNetworkingConstant.ROOT_DIRECTORY + File.separator + folder).toAbsolutePath().normalize();
            Path fP = fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(fP.toUri());
            if (resource.exists()) {
                return resource;
            }
        } catch (MalformedURLException ex) {
            log.error(ex.getMessage(), ex);
        }
        return null;
    }
}
