package com.social.networking.api.dto.group;

import com.social.networking.api.dto.InfoAdminDto;
import com.social.networking.api.dto.permission.PermissionDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class GroupDto extends InfoAdminDto {
    @ApiModelProperty(name = "name")
    private String name;
    @ApiModelProperty(name = "description")
    private String description;
    @ApiModelProperty(name = "kind")
    private Integer kind;
    @ApiModelProperty(name = "isSystemRole")
    private Boolean isSystemRole;
    @ApiModelProperty(name = "permissions")
    private List<PermissionDto> permissions;
}
