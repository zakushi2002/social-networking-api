package com.social.networking.api.form.post;

import com.social.networking.api.validation.PostKind;
import com.social.networking.api.validation.Privacy;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CreatePostForm {
    @ApiModelProperty(name = "title", required = true)
    @NotEmpty(message = "title cannot be null!")
    private String title;
    @ApiModelProperty(name = "content", required = true)
    @NotEmpty(message = "content cannot be null!")
    private String content;
    @ApiModelProperty(name = "kind", required = true)
    @PostKind
    private Integer kind;
    @ApiModelProperty(name = "privacy", required = true)
    @Privacy
    private Integer privacy;
    @ApiModelProperty(name = "communityId")
    private Long communityId;
    @ApiModelProperty(name = "topics")
    private Long[] topics;
    @ApiModelProperty(name = "status", required = true)
    private Integer status;
}
