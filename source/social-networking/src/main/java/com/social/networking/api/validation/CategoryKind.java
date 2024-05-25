package com.social.networking.api.validation;

import com.social.networking.api.validation.impl.CategoryKindValidation;

import javax.validation.Constraint;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CategoryKindValidation.class)
@Documented
public @interface CategoryKind {
    boolean allowNull() default false;

    String message() default "Category kind invalid!";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};
}
