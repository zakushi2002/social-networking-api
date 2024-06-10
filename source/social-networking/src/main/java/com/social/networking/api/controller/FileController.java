package com.social.networking.api.controller;

import com.social.networking.api.form.DeleteFileForm;
import com.social.networking.api.service.SocialNetworkingApiService;
import com.social.networking.api.dto.ApiMessageDto;
import com.social.networking.api.dto.UploadFileDto;
import com.social.networking.api.dto.aws.FileS3Dto;
import com.social.networking.api.form.UploadFileForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/v1/file")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
@ApiIgnore
public class FileController {
    @Autowired
    SocialNetworkingApiService socialNetworkingApiService;

    @PostMapping(value = "/upload/s3", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<UploadFileDto> upload(@Valid UploadFileForm uploadFileForm, BindingResult bindingResult) {
        return socialNetworkingApiService.uploadFileS3(uploadFileForm);
    }

    @GetMapping(value = "/load/s3/{fileName:.+}")
    public ResponseEntity<?> loadFileS3(@PathVariable String fileName) {
        FileS3Dto fileS3Dto = socialNetworkingApiService.loadFileAsResource(fileName);
        if (fileS3Dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .contentLength(fileS3Dto.getFileByte().length)
                .contentType(MediaType.parseMediaType(fileS3Dto.getFileType()))
                .cacheControl(CacheControl.maxAge(7776000, TimeUnit.SECONDS))
                .body(fileS3Dto.getFileByte());
    }

    @GetMapping(value = "/download/s3/{fileName:.+}")
    public ResponseEntity<ByteArrayResource> downloadFileS3(@PathVariable String fileName) {
        byte[] data = socialNetworkingApiService.loadFileAsResource(fileName).getFileByte();
        if (data == null) {
            return ResponseEntity.notFound().build();
        }
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity.ok()
                .contentLength(data.length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .cacheControl(CacheControl.maxAge(7776000, TimeUnit.SECONDS))
                .body(resource);
    }

    @PostMapping(value = "/delete/s3")
    public ApiMessageDto<String> deleteFile(@Valid @RequestBody DeleteFileForm deleteFileForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        socialNetworkingApiService.deleteFileS3(deleteFileForm.getFileName());
        apiMessageDto.setMessage("File deleted successfully by key - " + deleteFileForm.getFileName());
        return apiMessageDto;
    }
}
