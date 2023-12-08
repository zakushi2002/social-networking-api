package com.social.networking.api.view.form.profile.expert;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;
@Data
public class UpdateExpertAccountForm {
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
