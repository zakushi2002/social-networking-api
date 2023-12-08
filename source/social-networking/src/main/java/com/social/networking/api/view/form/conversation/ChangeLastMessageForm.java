package com.social.networking.api.view.form.conversation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class ChangeLastMessageForm {
    @ApiModelProperty(name = "id", required = true)
    @NotNull(message = "id can not be null!")
    private Long id;
    @ApiModelProperty(name = "lastMessage", required = true)
    @NotEmpty(message = "lastMessage can not be empty!")
    private String lastMessage;
}
