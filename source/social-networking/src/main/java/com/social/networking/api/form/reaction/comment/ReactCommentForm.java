package com.social.networking.api.form.reaction.comment;

import com.social.networking.api.validation.ReactionKind;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ReactCommentForm {
    @ApiModelProperty(name = "commentId",required = true)
    private Long commentId;
    @ApiModelProperty(name = "kind",required = true)
    @ReactionKind
    private Integer kind;
}
