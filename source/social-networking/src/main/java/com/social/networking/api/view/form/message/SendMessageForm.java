package com.social.networking.api.view.form.message;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SendMessageForm {
    @ApiModelProperty(name = "conversationId", required = true)
    private Long conversationId;
    @ApiModelProperty(name = "content", required = true)
    private String content;
}
