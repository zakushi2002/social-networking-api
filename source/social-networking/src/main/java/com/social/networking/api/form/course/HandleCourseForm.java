package com.social.networking.api.form.course;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
@Data
public class HandleCourseForm {
    @ApiModelProperty(name = "id", required = true)
    @NotNull(message = "id cannot be null!")
    private Long id;
}
