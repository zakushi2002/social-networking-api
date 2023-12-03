package com.social.networking.api.view.dto.conversation;

import com.social.networking.api.view.dto.InfoAdminDto;
import com.social.networking.api.view.dto.conversation.account.ConversationAccountDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ConversationDto extends InfoAdminDto {
    @ApiModelProperty(name = "name")
    private String name;
    @ApiModelProperty(name = "image")
    private String image;
    @ApiModelProperty(name = "kind")
    private Integer kind;
    @ApiModelProperty(name = "lastMessage")
    private String lastMessage;
    @ApiModelProperty(name = "accountList")
    private List<ConversationAccountDto> accountList;
    @ApiModelProperty(name = "modifiedBy")
    private String modifiedBy;
}
