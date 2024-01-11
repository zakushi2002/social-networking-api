package com.social.networking.api.validation;

import com.social.networking.api.validation.impl.ReactionKindValidation;

import javax.validation.Constraint;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ReactionKindValidation.class)
@Documented
public @interface ReactionKind {
    boolean allowNull() default false;

    String message() default "Reaction kind invalid!";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};
}
