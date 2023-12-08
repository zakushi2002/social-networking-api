package com.social.networking.api.view.dto.account;

import com.social.networking.api.view.dto.InfoAdminDto;
import com.social.networking.api.view.dto.category.CategoryDto;
import com.social.networking.api.view.dto.group.GroupDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
@Data
public class AccountProfileDto extends InfoAdminDto {
    @ApiModelProperty(name = "kind")
    private Integer kind;
    @ApiModelProperty(name = "email")
    private String email;
    @ApiModelProperty(name = "fullName")
    private String fullName;
    @ApiModelProperty(name = "avatar")
    private String avatar;
    @ApiModelProperty(name = "group")
    private GroupDto group;
    @ApiModelProperty(name = "dateOfBirth")
    private Date dateOfBirth;
    @ApiModelProperty(name = "phone")
    private String phone;
    @ApiModelProperty(name = "hospital")
    private CategoryDto hospital;
    @ApiModelProperty(name = "hospitalRole")
    private CategoryDto hospitalRole;
    @ApiModelProperty(name = "academicDegree")
    private CategoryDto academicDegree;
    @ApiModelProperty(name = "department")
    private CategoryDto department;
    @ApiModelProperty(name = "bio")
    private String bio;
}
