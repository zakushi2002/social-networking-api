package com.social.networking.api.form.reaction.comment;

import com.social.networking.api.validation.ReactionKind;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ReactCommentForm {
    @ApiModelProperty(name = "commentId",required = true)
    private Long commentId;
    @ApiModelProperty(name = "kind",required = true, notes = "1: Love, 2: Like, 3: Haha, 4: Wow, 5: Sad, 6: Angry")
    @ReactionKind
    private Integer kind;
}
