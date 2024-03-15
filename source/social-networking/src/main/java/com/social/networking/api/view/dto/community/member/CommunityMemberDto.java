package com.social.networking.api.view.dto.community.member;

import com.social.networking.api.view.dto.InfoAdminDto;
import com.social.networking.api.view.dto.account.AccountProfileDto;
import com.social.networking.api.view.dto.category.CategoryDto;
import lombok.Data;

@Data
public class CommunityMemberDto extends InfoAdminDto {
    private AccountProfileDto account;
    private CategoryDto community;
    private Integer kind;
}
