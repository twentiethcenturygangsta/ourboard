package com.github.twentiethcenturygangsta.ourboard.validation;

import com.github.twentiethcenturygangsta.ourboard.annoatation.validator.ValidMemberId;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IdValidator implements ConstraintValidator<ValidMemberId, String> {

    private final int MIN_MEMBER_ID_SIZE = 4;
    private final int MAX_MEMBER_ID_SIZE = 12;

    @Override
    public boolean isValid(String memberId, ConstraintValidatorContext constraintValidatorContext) {
        if (memberId == null) {
            return false;
        }
        return MIN_MEMBER_ID_SIZE <= memberId.length() && memberId.length() <= MAX_MEMBER_ID_SIZE;
    }
}
