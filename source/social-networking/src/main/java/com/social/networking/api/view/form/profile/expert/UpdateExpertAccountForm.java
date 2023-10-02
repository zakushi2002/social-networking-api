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
    @ApiModelProperty(name = "hospital", required = true)
    @NotEmpty(message = "hospital cannot be empty!")
    private String hospital;
    @ApiModelProperty(name = "hospitalRole", required = true)
    @NotEmpty(message = "hospitalRole cannot be empty!")
    private String hospitalRole;
    @ApiModelProperty(name = "academicDegree", required = true)
    @NotNull(message = "academicDegree cannot be null!")
    private Integer academicDegree;
    @ApiModelProperty(name = "department", required = true)
    @NotEmpty(message = "department cannot be empty!")
    private String department;
    @ApiModelProperty(name = "bio")
    private String bio;
    @ApiModelProperty(name = "newPassword")
    @Size(min = 8, message = "newPassword must be at least 8 characters")
    private String newPassword;
    @ApiModelProperty(name = "oldPassword", required = true)
    @NotEmpty(message = "oldPassword cannot be empty!")
    private String oldPassword;
}
