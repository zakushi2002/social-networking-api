package com.social.networking.api.view.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class InfoAdminDto {
    @ApiModelProperty(name = "id")
    private Long id;
    @ApiModelProperty(name = "status")
    private Integer status;
    @ApiModelProperty(name = "modifiedDate")
    private Date modifiedDate;
    @ApiModelProperty(name = "createdDate")
    private Date createdDate;
}
