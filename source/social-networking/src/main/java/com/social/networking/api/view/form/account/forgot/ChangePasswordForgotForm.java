package com.social.networking.api.view.form.account.forgot;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class ChangePasswordForgotForm {
    @ApiModelProperty(name = "email", required = true)
    @NotEmpty(message = "email cannot be empty!")
    @Email
    private String email;
    @ApiModelProperty(name = "newPassword", required = true)
    @NotEmpty(message = "newPassword cannot be empty!")
    @Size(min = 8, message = "password must be at least 8 characters")
    private String newPassword;
}