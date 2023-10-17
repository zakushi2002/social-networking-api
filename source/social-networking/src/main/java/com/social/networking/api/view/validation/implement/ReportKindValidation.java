package com.social.networking.api.view.validation.implement;

import com.social.networking.api.constant.SocialNetworkingConstant;
import com.social.networking.api.view.validation.ReportKind;

import javax.validation.ConstraintValidator;
import java.util.Objects;

public class ReportKindValidation implements ConstraintValidator<ReportKind, Integer> {
    private boolean allowNull;

    @Override
    public void initialize(ReportKind constraintAnnotation) {
        allowNull = constraintAnnotation.allowNull();
    }

    @Override
    public boolean isValid(Integer reportKind, javax.validation.ConstraintValidatorContext constraintValidatorContext) {
        if (reportKind == null && allowNull) {
            return true;
        }
        return Objects.equals(reportKind, SocialNetworkingConstant.REPORT_KIND_POST)
                || Objects.equals(reportKind, SocialNetworkingConstant.REPORT_KIND_COMMENT)
                || Objects.equals(reportKind, SocialNetworkingConstant.REPORT_KIND_ACCOUNT);
    }
}
