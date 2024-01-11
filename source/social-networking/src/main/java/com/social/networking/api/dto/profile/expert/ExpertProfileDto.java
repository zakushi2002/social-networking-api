package com.social.networking.api.dto.profile.expert;

import com.social.networking.api.dto.InfoAdminDto;
import com.social.networking.api.dto.category.CategoryDto;
import com.social.networking.api.dto.group.GroupDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class ExpertProfileDto extends InfoAdminDto {
    @ApiModelProperty(name = "expertKind")
    private Integer expertKind;
    @ApiModelProperty(name = "expertEmail")
    private String expertEmail;
    @ApiModelProperty(name = "expertFullName")
    private String expertFullName;
    @ApiModelProperty(name = "expertAvatar")
    private String expertAvatar;
    @ApiModelProperty(name = "expertGroup")
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
