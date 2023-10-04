package com.social.networking.api.view.dto.reaction;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PostReactionDto {
    @ApiModelProperty(name = "id")
    private Long id;
    @ApiModelProperty(name = "kind")
    private Integer kind;
    @ApiModelProperty(name = "postId")
    private Long postId;
    @ApiModelProperty(name = "accountId")
    private Long accountId;
}
