package com.social.networking.api.form.reaction.post;

import com.social.networking.api.validation.ReactionKind;
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
