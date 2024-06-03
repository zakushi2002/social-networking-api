package com.social.networking.api.validation.impl;

import com.social.networking.api.constant.SocialNetworkingConstant;
import com.social.networking.api.validation.CourseMoneyHistoryState;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class CourseMoneyHistoryStateValidation implements ConstraintValidator<CourseMoneyHistoryState, Integer> {
    private boolean allowNull;

    @Override
    public void initialize(CourseMoneyHistoryState constraintAnnotation) {
        allowNull = constraintAnnotation.allowNull();
    }

    @Override
    public boolean isValid(Integer courseMoneyHistoryState, ConstraintValidatorContext constraintValidatorContext) {
        if (courseMoneyHistoryState == null && allowNull) {
            return true;
        }
        return Objects.equals(courseMoneyHistoryState, SocialNetworkingConstant.COURSE_MONEY_HISTORY_STATE_PENDING)
                || Objects.equals(courseMoneyHistoryState, SocialNetworkingConstant.COURSE_MONEY_HISTORY_STATE_SUCCESS)
                || Objects.equals(courseMoneyHistoryState, SocialNetworkingConstant.COURSE_MONEY_HISTORY_STATE_FAILED)
                || Objects.equals(courseMoneyHistoryState, SocialNetworkingConstant.COURSE_MONEY_HISTORY_STATE_CANCEL);
    }
}