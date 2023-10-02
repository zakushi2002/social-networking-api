package com.social.networking.api.view.validation;

import com.social.networking.api.view.validation.implement.PrivacyValidation;

import javax.validation.Constraint;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PrivacyValidation.class)
@Documented
public @interface Privacy {
    boolean allowNull() default false;
    String message() default "Privacy invalid!";
    Class<?>[] groups() default {};
    Class<?>[] payload() default {};
}
