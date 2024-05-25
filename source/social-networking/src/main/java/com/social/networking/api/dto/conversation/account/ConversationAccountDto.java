package com.social.networking.api.dto.conversation.account;

import com.social.networking.api.dto.InfoAdminDto;
import com.social.networking.api.dto.account.AccountDto;
import com.social.networking.api.dto.conversation.ConversationDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ConversationAccountDto extends InfoAdminDto {
    @ApiModelProperty(name = "conversation")
    private ConversationDto conversation;
    @ApiModelProperty(name = "account")
    private AccountDto account;
}
