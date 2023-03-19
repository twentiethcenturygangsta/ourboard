package com.github.twentiethcenturygangsta.ourboard.validation;

import com.github.twentiethcenturygangsta.ourboard.annoatation.validator.ValidPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {
    private final int MIN_PASSWORD_SIZE = 8;
    private final int MAX_PASSWORD_SIZE = 31;
    private final String regexPassword = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{" + MIN_PASSWORD_SIZE
            + "," + MAX_PASSWORD_SIZE + "}$";

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        return password.matches(regexPassword);
    }
}
