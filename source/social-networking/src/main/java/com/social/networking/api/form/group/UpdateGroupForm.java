package com.social.networking.api.form.group;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel
public class UpdateGroupForm {
    @ApiModelProperty(name = "id", required = true)
    @NotNull(message = "id cannot be null!")
    private Long id;
    @ApiModelProperty(name = "name")
    private String name;
    @ApiModelProperty(name = "description")
    private String description;
    /*@ApiModelProperty(name = "permissions", required = true)
    @NotNull(message = "permissions cannot be null!")
    private Long[] permissions;*/
}
