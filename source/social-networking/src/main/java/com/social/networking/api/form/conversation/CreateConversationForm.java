package com.social.networking.api.form.conversation;

import com.social.networking.api.validation.ConversationKind;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateConversationForm {
    @ApiModelProperty(name = "name")
    private String name;
    @ApiModelProperty(name = "image")
    private String image;
    @ApiModelProperty(name = "kind", required = true)
    @ConversationKind
    private Integer kind;
    @ApiModelProperty(name = "lastMessage")
    private String lastMessage;
    @ApiModelProperty(name = "accountIds", required = true)
    @NotNull(message = "accountIds can not be null!")
    private Long[] accountIds;
}
