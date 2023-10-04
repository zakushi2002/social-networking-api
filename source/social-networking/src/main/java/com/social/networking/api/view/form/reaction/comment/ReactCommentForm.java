package com.social.networking.api.view.form.reaction.comment;

import com.social.networking.api.view.validation.ReactionKind;
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
