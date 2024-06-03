package com.social.networking.api.form.course.money;

import com.social.networking.api.validation.CourseMoneyHistoryState;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ChangeStateCourseMoneyHistoryForm {
    @NotNull(message = "id cannot be null!")
    private Long id;
    @CourseMoneyHistoryState
    private Integer state;
}
