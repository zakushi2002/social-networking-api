package com.social.networking.api.view.form.post;

import com.social.networking.api.view.validation.PostKind;
import com.social.networking.api.view.validation.Privacy;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CreatePostForm {
    @ApiModelProperty(name = "content", required = true)
    @NotEmpty(message = "content cannot be null!")
    private String content;
    @ApiModelProperty(name = "kind", required = true)
    @PostKind
    private Integer kind;
    @ApiModelProperty(name = "privacy", required = true)
    @Privacy
    private Integer privacy;
    @ApiModelProperty(name = "status", required = true)
    private Integer status;
}
