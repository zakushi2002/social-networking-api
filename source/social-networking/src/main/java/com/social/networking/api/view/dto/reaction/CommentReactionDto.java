package com.social.networking.api.view.dto.reaction;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CommentReactionDto {
    @ApiModelProperty(name = "id")
    private Long id;
    @ApiModelProperty(name = "kind")
    private Integer kind;
    @ApiModelProperty(name = "commentId")
    private Long commentId;
    @ApiModelProperty(name = "ownerCommentId")
    private Long ownerCommentId;
    @ApiModelProperty(name = "accountId")
    private Long accountId;
}
