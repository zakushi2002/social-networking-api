package com.social.networking.api.form.conversation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UpdateConversationForm {
    @ApiModelProperty(name = "id", required = true)
    @NotNull(message = "id can not be null!")
    private Long id;
    @ApiModelProperty(name = "name", required = true)
    @NotEmpty(message = "name can not be empty!")
    private String name;
    @ApiModelProperty(name = "image", required = true)
    @NotEmpty(message = "image can not be empty!")
    private String image;
}
