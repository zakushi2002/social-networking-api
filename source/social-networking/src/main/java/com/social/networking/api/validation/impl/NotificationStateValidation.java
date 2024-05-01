package com.social.networking.api.validation.impl;

import com.social.networking.api.constant.SocialNetworkingConstant;
import com.social.networking.api.validation.NotificationState;
import org.apache.commons.lang.ObjectUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotificationStateValidation implements ConstraintValidator<NotificationState, Integer> {
    private boolean allowNull;

    @Override
    public void initialize(NotificationState constraintAnnotation) {
        allowNull = constraintAnnotation.allowNull();
    }

    @Override
    public boolean isValid(Integer notificationState, ConstraintValidatorContext constraintValidatorContext) {
        if (notificationState == null && allowNull) {
            return true;
        }
        return ObjectUtils.equals(notificationState, SocialNetworkingConstant.NOTIFICATION_STATE_SENT)
                || ObjectUtils.equals(notificationState, SocialNetworkingConstant.NOTIFICATION_STATE_READ);
    }
}
