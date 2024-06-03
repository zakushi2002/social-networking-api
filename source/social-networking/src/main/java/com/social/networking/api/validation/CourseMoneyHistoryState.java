package com.social.networking.api.validation;

import com.social.networking.api.validation.impl.CourseMoneyHistoryStateValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CourseMoneyHistoryStateValidation.class)
@Documented
public @interface CourseMoneyHistoryState {
    boolean allowNull() default false;

    String message() default "Course money history state invalid.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
