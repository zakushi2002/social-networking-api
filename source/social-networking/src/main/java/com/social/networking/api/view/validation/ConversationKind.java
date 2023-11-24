package com.social.networking.api.view.validation;

import com.social.networking.api.view.validation.implement.ConversationKindValidation;

import javax.validation.Constraint;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ConversationKindValidation.class)
@Documented
public @interface ConversationKind {
    boolean allowNull() default false;

    String message() default "Conversation kind invalid!";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};
}
