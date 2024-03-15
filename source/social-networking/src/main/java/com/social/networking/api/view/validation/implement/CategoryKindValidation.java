package com.social.networking.api.view.validation.implement;

import com.social.networking.api.constant.SocialNetworkingConstant;
import com.social.networking.api.view.validation.CategoryKind;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class CategoryKindValidation implements ConstraintValidator<CategoryKind, Integer> {
    private boolean allowNull;

    @Override
    public void initialize(CategoryKind constraintAnnotation) {
        allowNull = constraintAnnotation.allowNull();
    }

    @Override
    public boolean isValid(Integer categoryKind, ConstraintValidatorContext constraintValidatorContext) {
        if (categoryKind == null && allowNull) {
            return true;
        }
        return Objects.equals(categoryKind, SocialNetworkingConstant.CATEGORY_KIND_HOSPITAL)
                || Objects.equals(categoryKind, SocialNetworkingConstant.CATEGORY_KIND_HOSPITAL_ROLE)
                || Objects.equals(categoryKind, SocialNetworkingConstant.CATEGORY_KIND_DEPARTMENT)
                || Objects.equals(categoryKind, SocialNetworkingConstant.CATEGORY_KIND_ACADEMIC_DEGREE)
                || Objects.equals(categoryKind, SocialNetworkingConstant.CATEGORY_KIND_COMMUNITY)
                || Objects.equals(categoryKind, SocialNetworkingConstant.CATEGORY_KIND_TOPIC);
    }
}
