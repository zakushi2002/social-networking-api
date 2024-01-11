package com.social.networking.api.validation;

import com.social.networking.api.validation.impl.ReportKindValidation;

import javax.validation.Constraint;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ReportKindValidation.class)
@Documented
public @interface ReportKind {
    boolean allowNull() default false;

    String message() default "Report kind invalid!";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};
}
