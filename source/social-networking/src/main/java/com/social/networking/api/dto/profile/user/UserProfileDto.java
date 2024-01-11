package com.social.networking.api.dto.profile.user;

import com.social.networking.api.dto.InfoAdminDto;
import com.social.networking.api.dto.group.GroupDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class UserProfileDto extends InfoAdminDto {
    @ApiModelProperty(name = "userKind")
    private Integer userKind;
    @ApiModelProperty(name = "userEmail")
    private String userEmail;
    @ApiModelProperty(name = "userFullName")
    private String userFullName;
    @ApiModelProperty(name = "userAvatar")
    private String userAvatar;
    @ApiModelProperty(name = "userGroup")
    private GroupDto userGroup;
    @ApiModelProperty(name = "dateOfBirth")
    private Date dateOfBirth;
    @ApiModelProperty(name = "phone")
    private String phone;
    @ApiModelProperty(name = "bio")
    private String bio;
}
