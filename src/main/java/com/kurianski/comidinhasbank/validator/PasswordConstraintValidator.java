package com.kurianski.comidinhasbank.validator;


import org.passay.*;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public void initialize(ValidPassword constraintAnnotation) {

    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        PasswordValidator validator = new PasswordValidator(Arrays.asList(
            new LengthRule(6, 10),
            new CharacterRule(EnglishCharacterData.UpperCase, 1),
            new CharacterRule(EnglishCharacterData.LowerCase, 1),
            new CharacterRule(EnglishCharacterData.Digit, 1),
            new CharacterRule(EnglishCharacterData.Special, 1),
            new WhitespaceRule()
        ));

        RuleResult result = validator.validate(new PasswordData(password));

        if(result.isValid()) {
            return true;
        }

        String messages = String.join(", ", validator.getMessages(result));

        constraintValidatorContext.disableDefaultConstraintViolation();
        constraintValidatorContext
                .buildConstraintViolationWithTemplate(messages)
                .addConstraintViolation();

        return false;
    }
}
