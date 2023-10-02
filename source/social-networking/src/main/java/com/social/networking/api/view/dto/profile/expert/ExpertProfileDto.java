package com.social.networking.api.view.dto.profile.expert;

import com.social.networking.api.view.dto.InfoAdminDto;
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
    private String hospital;
    @ApiModelProperty(name = "hospitalRole")
    private String hospitalRole;
    @ApiModelProperty(name = "academicDegree")
    private Integer academicDegree;
    @ApiModelProperty(name = "department")
    private String department;
    @ApiModelProperty(name = "bio")
    private String bio;
}
