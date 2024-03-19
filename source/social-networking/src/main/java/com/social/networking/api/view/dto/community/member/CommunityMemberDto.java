package com.social.networking.api.view.dto.community.member;

import com.social.networking.api.dto.InfoAdminDto;
import com.social.networking.api.dto.account.AccountDto;
import com.social.networking.api.dto.category.CategoryDto;
import lombok.Data;

@Data
public class CommunityMemberDto extends InfoAdminDto {
    private AccountDto account;
    private CategoryDto community;
    private Integer kind;
}
