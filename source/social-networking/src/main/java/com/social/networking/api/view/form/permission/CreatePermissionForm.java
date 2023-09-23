package com.social.networking.api.view.form.permission;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@ApiModel
public class CreatePermissionForm {
    @ApiModelProperty(name = "name", required = true)
    @NotEmpty(message = "name cannot be empty!")
    private String name;
    @ApiModelProperty(name = "action", required = true)
    @NotEmpty(message = "action cannot be empty!")
    private String action;
    @ApiModelProperty(name = "description", required = true)
    @NotEmpty(message = "description cannot be empty!")
    private String description;
    @ApiModelProperty(name = "showMenu", required = true)
    @NotNull(message = "showMenu cannot be null!")
    private Boolean showMenu;
    @ApiModelProperty(name = "nameGroup", required = true)
    @NotEmpty(message = "nameGroup cannot be empty!")
    private String nameGroup;
    @ApiModelProperty(name = "code", required = true)
    @NotEmpty(message = "code cannot be empty!")
    private String code;
}
