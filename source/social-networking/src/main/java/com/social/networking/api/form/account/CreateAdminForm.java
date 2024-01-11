package com.social.networking.api.form.account;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@ApiModel
public class CreateAdminForm {
    @ApiModelProperty(name = "email", required = true)
    @NotEmpty(message = "email cannot be empty!")
    @Email
    private String email;
    @ApiModelProperty(name = "password", required = true)
    @NotEmpty(message = "password cannot be empty!")
    @Size(min = 8, message = "password must be at least 8 characters")
    private String password;
    @ApiModelProperty(name = "fullName", required = true)
    @NotEmpty(message = "fullName cannot be empty!")
    private String fullName;
    @ApiModelProperty(name = "avatarPath")
    private String avatarPath;
    @ApiModelProperty(name = "groupId", required = true)
    @NotNull(message = "groupId cannot be null!")
    private Long groupId;
}
