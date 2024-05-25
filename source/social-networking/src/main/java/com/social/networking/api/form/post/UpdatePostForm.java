package com.social.networking.api.form.post;

import com.social.networking.api.validation.Privacy;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UpdatePostForm {
    @ApiModelProperty(name = "id", required = true)
    @NotNull(message = "id cannot be null!")
    private Long id;
    @ApiModelProperty(name = "title")
    private String title;
    @ApiModelProperty(name = "content", required = true)
    @NotEmpty(message = "content cannot be null!")
    private String content;
    @ApiModelProperty(name = "privacy", notes = "1: public, 2: private")
    @Privacy(allowNull = true)
    private Integer privacy;
    @ApiModelProperty(name = "topics")
    private Long[] topics;
}
