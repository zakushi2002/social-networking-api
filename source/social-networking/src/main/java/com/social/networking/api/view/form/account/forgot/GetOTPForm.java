package com.social.networking.api.view.form.account.forgot;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class GetOTPForm {
    @ApiModelProperty(name = "email", required = true)
    @NotEmpty(message = "email cannot be empty!")
    @Email
    private String email;
}