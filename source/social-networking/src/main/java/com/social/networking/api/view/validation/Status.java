package com.social.networking.api.view.validation;

import com.social.networking.api.view.validation.implement.StatusValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StatusValidation.class)
@Documented
public @interface Status {
    boolean allowNull() default false;

    String message() default "Status invalid!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
