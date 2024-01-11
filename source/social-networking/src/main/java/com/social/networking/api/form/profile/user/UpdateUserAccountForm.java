package com.social.networking.api.form.profile.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class UpdateUserAccountForm {
    @ApiModelProperty(name = "fullName", required = true)
    @NotEmpty(message = "fullName cannot be empty!")
    private String fullName;
    @ApiModelProperty(name = "avatarPath")
    private String avatarPath;
    @ApiModelProperty(name = "dateOfBirth")
    @Past(message = "dateOfBirth must be in the past!")
    private Date dateOfBirth;
    @ApiModelProperty(name = "phone")
    @Size(min = 10, max = 11, message = "phone must be at least 10 characters and at most 11 characters")
    private String phone;
    @ApiModelProperty(name = "bio")
    private String bio;
}
