package com.social.networking.api.view.dto.relationship;

import com.social.networking.api.view.dto.InfoAdminDto;
import com.social.networking.api.view.dto.account.AccountDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RelationshipDto extends InfoAdminDto {
    @ApiModelProperty(name = "account")
    private AccountDto account;
    @ApiModelProperty(name = "follower")
    private AccountDto follower;
}
