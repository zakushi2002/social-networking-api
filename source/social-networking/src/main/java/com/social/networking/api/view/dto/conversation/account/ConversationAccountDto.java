package com.social.networking.api.view.dto.conversation.account;

import com.social.networking.api.view.dto.InfoAdminDto;
import com.social.networking.api.view.dto.account.AccountDto;
import com.social.networking.api.view.dto.conversation.ConversationDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ConversationAccountDto extends InfoAdminDto {
    @ApiModelProperty(name = "conversation")
    private ConversationDto conversation;
    @ApiModelProperty(name = "account")
    private AccountDto account;
}
