package io.papayankey.taskman.task;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TaskIdValidator implements ConstraintValidator<ValidId, String> {
    @Override
    public boolean isValid(String id, ConstraintValidatorContext constraintValidatorContext) {
        try {
            Integer.parseInt(id);
        } catch (NumberFormatException exception) {
            return false;
        }
        return true;
    }
}
