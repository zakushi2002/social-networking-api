package com.social.networking.api.validation.impl;

import com.social.networking.api.constant.SocialNetworkingConstant;
import com.social.networking.api.validation.Privacy;

import javax.validation.ConstraintValidator;
import java.util.Objects;

public class PrivacyValidation implements ConstraintValidator<Privacy, Integer> {
    private boolean allowNull;

    @Override
    public void initialize(Privacy constraintAnnotation) {
        allowNull = constraintAnnotation.allowNull();
    }

    @Override
    public boolean isValid(Integer privacy, javax.validation.ConstraintValidatorContext constraintValidatorContext) {
        if (privacy == null && allowNull) {
            return true;
        }
        return Objects.equals(privacy, SocialNetworkingConstant.PRIVACY_PUBLIC)
                || Objects.equals(privacy, SocialNetworkingConstant.PRIVACY_PRIVATE);
    }
}