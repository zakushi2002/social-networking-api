package com.social.networking.api.view.validation;

import com.social.networking.api.view.validation.implement.ReactionKindValidation;

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
