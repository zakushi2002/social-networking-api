package com.social.networking.api.form.course;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class UpdateCourseForm {
    @NotNull(message = "Id cannot be null")
    private Long id;
    private String title;
    private String description;
    private Integer slots;
    @Future(message = "Start date must be in the future")
    @ApiModelProperty(example = "dd/MM/yyyy HH:mm:ss")
    private Date startDate;
    @Future(message = "End date must be in the future")
    @ApiModelProperty(example = "dd/MM/yyyy HH:mm:ss")
    private Date endDate;
    private Integer fee;
}
