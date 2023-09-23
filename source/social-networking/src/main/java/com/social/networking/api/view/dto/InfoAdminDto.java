package com.social.networking.api.view.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InfoAdminDto {
    @ApiModelProperty(name = "id")
    private Long id;
    @ApiModelProperty(name = "status")
    private Integer status;
    @ApiModelProperty(name = "modifiedDate")
    private LocalDateTime modifiedDate;
    @ApiModelProperty(name = "createdDate")
    private LocalDateTime createdDate;
}
