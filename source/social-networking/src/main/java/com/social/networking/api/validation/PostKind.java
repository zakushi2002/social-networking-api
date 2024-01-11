package com.social.networking.api.validation;

import com.social.networking.api.validation.impl.PostKindValidation;

import javax.validation.Constraint;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PostKindValidation.class)
@Documented
public @interface PostKind {
    boolean allowNull() default false;

    String message() default "Post kind invalid!";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};
}
