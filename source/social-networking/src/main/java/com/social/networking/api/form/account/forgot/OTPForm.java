package com.social.networking.api.form.account.forgot;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class OTPForm {
    @ApiModelProperty(name = "email", required = true)
    @NotEmpty(message = "email cannot be empty!")
    @Email
    private String email;
    @ApiModelProperty(name = "otp", required = true)
    @NotEmpty(message = "otp cannot be empty!")
    private String otp;
}