package com.social.networking.api.view.dto.profile.expert;

import com.social.networking.api.view.dto.InfoAdminDto;
import com.social.networking.api.view.dto.category.CategoryDto;
import com.social.networking.api.view.dto.group.GroupDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class ExpertProfileDto extends InfoAdminDto {
    @ApiModelProperty(name = "kind")
    private Integer expertKind;
    @ApiModelProperty(name = "email")
    private String expertEmail;
    @ApiModelProperty(name = "fullName")
    private String expertFullName;
    @ApiModelProperty(name = "avatar")
    private String expertAvatar;
    @ApiModelProperty(name = "group")
    private GroupDto expertGroup;
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
