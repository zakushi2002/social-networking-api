package com.social.networking.api.view.form.group;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@ApiModel
public class CreateGroupForm {
    @ApiModelProperty(name = "name", required = true)
    @NotEmpty(message = "name cannot be empty!")
    private String name;
    @ApiModelProperty(name = "description", required = true)
    @NotEmpty(message = "description cannot be empty!")
    private String description;
    @ApiModelProperty(name = "permissions", required = true)
    @NotNull(message = "permissions cannot be null!")
    private Long[] permissions;
    @ApiModelProperty(name = "kind", required = true)
    @NotNull(message = "kind cannot be null!")
    private Integer kind;
}
