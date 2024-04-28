package com.social.networking.api.form.course;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class CreateCourseForm {
    @NotEmpty(message = "Title cannot be empty")
    private String title;
    @NotEmpty(message = "Description cannot be empty")
    private String description;
    @NotNull(message = "Expert id cannot be null")
    private Long expertId;
    @NotNull(message = "Slots cannot be null")
    private Integer slots;
    @NotNull(message = "Start date cannot be null")
    @Future(message = "Start date must be in the future")
    @ApiModelProperty(example = "dd/MM/yyyy HH:mm:ss")
    private Date startDate;
    @NotNull(message = "End date cannot be null")
    @Future(message = "End date must be in the future")
    @ApiModelProperty(example = "dd/MM/yyyy HH:mm:ss")
    private Date endDate;
    private Long topicId;
    private Integer fee;
    private Integer status;
}
