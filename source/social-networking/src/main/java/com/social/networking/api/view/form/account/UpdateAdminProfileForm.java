package com.social.networking.api.view.form.account;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class UpdateAdminProfileForm {
    @ApiModelProperty(name = "newPassword")
    @Size(min = 8, message = "newPassword must be at least 8 characters")
    private String newPassword;
    @ApiModelProperty(name = "oldPassword", required = true)
    @NotEmpty(message = "oldPassword cannot be empty!")
    private String oldPassword;
    @ApiModelProperty(name = "fullName", required = true)
    @NotEmpty(message = "fullName cannot be empty!")
    private String fullName;
    @ApiModelProperty(name = "avatarPath")
    private String avatarPath;
}
