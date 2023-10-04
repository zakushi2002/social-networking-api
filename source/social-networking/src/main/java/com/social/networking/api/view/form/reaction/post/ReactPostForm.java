package com.social.networking.api.view.form.reaction.post;

import com.social.networking.api.view.validation.ReactionKind;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ReactPostForm {
    @ApiModelProperty(name = "postId",required = true)
    private Long postId;
    @ApiModelProperty(name = "kind",required = true)
    @ReactionKind
    private Integer kind;
}
