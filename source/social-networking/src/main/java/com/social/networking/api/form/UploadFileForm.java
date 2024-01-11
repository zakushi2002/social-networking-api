package com.social.networking.api.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UploadFileForm {
    /**
     * Type of file upload.
     */
    @ApiModelProperty(name = "type", required = true)
    @NotEmpty(message = "type cannot be empty!")
    private String type;
    @ApiModelProperty(name = "file", required = true)
    @NotNull(message = "file cannot be null!")
    private MultipartFile file;
}
