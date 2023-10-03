package com.social.networking.api.view.form.post.bookmark;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateBookmarkForm {
    @ApiModelProperty(notes = "postId", required = true)
    @NotNull(message = "postId can be not null")
    private Long postId;
}
