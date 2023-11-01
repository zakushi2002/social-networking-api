package com.social.networking.api.view.validation;

import com.social.networking.api.view.validation.implement.NotificationKindValidation;

import javax.validation.Constraint;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotificationKindValidation.class)
@Documented
public @interface NotificationKind {
    boolean allowNull() default false;

    String message() default "Notification kind invalid!";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};
}
