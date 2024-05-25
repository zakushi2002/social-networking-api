package com.social.networking.api.dto.comment;

import com.social.networking.api.dto.InfoAdminDto;
import com.social.networking.api.dto.account.AccountDto;
import com.social.networking.api.dto.post.PostDto;
import com.social.networking.api.dto.reaction.CommentReactionDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class CommentDto extends InfoAdminDto {
    @ApiModelProperty(name = "commentContent")
    private String commentContent;
    @ApiModelProperty(name = "commentDepth")
    private Integer commentDepth;
    @ApiModelProperty(name = "post")
    private PostDto post;
    @ApiModelProperty(name = "owner")
    private AccountDto owner;
    @ApiModelProperty(name = "parentComment")
    private CommentDto parentComment;
    @ApiModelProperty(name = "commentReactions")
    private List<CommentReactionDto> commentReactions;
    @ApiModelProperty(name = "ownerIdOfPost")
    private Long ownerIdOfPost;
    @ApiModelProperty(name = "taggedAccountIds")
    private List<Long> taggedAccountIds;
}
