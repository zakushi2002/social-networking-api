package com.social.networking.api.view.form.post;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ApprovePostForm {
    @ApiModelProperty(name = "id", required = true)
    @NotNull(message = "id cannot be null!")
    private Long id;
}
