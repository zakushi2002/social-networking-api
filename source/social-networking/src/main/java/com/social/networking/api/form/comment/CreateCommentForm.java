package com.social.networking.api.form.comment;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CreateCommentForm {
    @ApiModelProperty(name = "postId", required = true)
    private Long postId;
    @ApiModelProperty(name = "parentId")
    private Long parentId;
    @ApiModelProperty(name = "commentContent", required = true)
    @NotEmpty(message = "commentContent cannot be empty!")
    private String commentContent;
}
