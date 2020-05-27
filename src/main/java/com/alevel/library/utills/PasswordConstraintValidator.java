package com.alevel.library.utills;

import com.alevel.library.exceptions.PasswordValidationException;
import org.passay.*;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {
    public void initialize(ValidPassword constraint) {
    }

    public boolean isValid(String password, ConstraintValidatorContext context) {
        List<Rule> rules = new ArrayList<>();
        rules.add(new LengthRule(8, 100));
        rules.add(new CharacterRule(EnglishCharacterData.UpperCase, 1));
        rules.add(new CharacterRule(EnglishCharacterData.LowerCase, 1));
        rules.add(new CharacterRule(EnglishCharacterData.Digit, 1));
        rules.add(new WhitespaceRule());
        PasswordValidator validator = new PasswordValidator(rules);
        RuleResult result = validator.validate(new PasswordData(password));
        if (result.isValid()) {
            return true;
        }
        List<String> messages = validator.getMessages(result);

        String messageTemplate = String.join(",", messages);
        throw new PasswordValidationException(messageTemplate);
    }
}
