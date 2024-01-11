package com.social.networking.api.form.profile.expert;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;
import java.util.Date;

@Data
public class CreateExpertAccountForm {
    @Email
    @NotEmpty(message = "email cannot be empty!")
    @ApiModelProperty(name = "email", required = true)
    private String email;
    @NotEmpty(message = "password cannot be empty!")
    @ApiModelProperty(name = "password", required = true)
    @Size(min = 8, message = "password must be at least 8 characters")
    private String password;
    @ApiModelProperty(name = "fullName", required = true)
    @NotEmpty(message = "fullName cannot be empty!")
    private String fullName;
    @ApiModelProperty(name = "avatarPath", required = true)
    @NotEmpty(message = "avatarPath cannot be empty!")
    private String avatarPath;
    @ApiModelProperty(name = "dateOfBirth", required = true)
    @Past(message = "dateOfBirth must be in the past!")
    private Date dateOfBirth;
    @ApiModelProperty(name = "phone", required = true)
    @NotEmpty(message = "phone cannot be empty!")
    @Size(min = 10, max = 11, message = "phone must be at least 10 characters and at most 11 characters")
    private String phone;
    @ApiModelProperty(name = "hospitalId", required = true)
    @NotNull(message = "hospitalId cannot be null!")
    private Long hospitalId;
    @ApiModelProperty(name = "hospitalRoleId", required = true)
    @NotNull(message = "hospitalRoleId cannot be null!")
    private Long hospitalRoleId;
    @ApiModelProperty(name = "academicDegreeId", required = true)
    @NotNull(message = "academicDegreeId cannot be null!")
    private Long academicDegreeId;
    @ApiModelProperty(name = "departmentId", required = true)
    @NotNull(message = "departmentId cannot be null!")
    private Long departmentId;
    @ApiModelProperty(name = "bio")
    private String bio;

}
