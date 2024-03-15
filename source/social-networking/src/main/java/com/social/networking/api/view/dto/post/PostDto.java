package com.social.networking.api.view.dto.post;

import com.social.networking.api.view.dto.InfoAdminDto;
import com.social.networking.api.view.dto.account.AccountDto;
import com.social.networking.api.view.dto.category.CategoryDto;
import com.social.networking.api.view.dto.comment.CommentDto;
import com.social.networking.api.view.dto.reaction.PostReactionDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PostDto extends InfoAdminDto {
    @ApiModelProperty(name = "title")
    private String title;
    @ApiModelProperty(name = "content")
    private String content;
    @ApiModelProperty(name = "owner")
    private AccountDto owner;
    @ApiModelProperty(name = "kind")
    private Integer kind;
    @ApiModelProperty(name = "privacy")
    private Integer privacy;
    @ApiModelProperty(name = "postReactions")
    private List<PostReactionDto> postReactions;
    @ApiModelProperty(name = "moderatedDate")
    private Date moderatedDate;
    private List<CommentDto> commentList;
    @ApiModelProperty(name = "community")
    private CategoryDto community;
    @ApiModelProperty(name = "topics")
    private List<CategoryDto> topics;
}
