package com.social.networking.api.view.form.comment;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UpdateCommentForm {
    @ApiModelProperty(name = "id", required = true)
    @NotNull(message = "id cannot be null!")
    private Long id;
    @ApiModelProperty(name = "commentContent", required = true)
    @NotEmpty(message = "commentContent cannot be empty!")
    private String commentContent;
}
