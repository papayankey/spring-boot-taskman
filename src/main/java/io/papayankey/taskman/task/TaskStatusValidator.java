package io.papayankey.taskman.task;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class TaskStatusValidator implements ConstraintValidator<ValidTaskStatus, String> {
    @Override
    public boolean isValid(String status, ConstraintValidatorContext constraintValidatorContext) {
        if (status == null || status.isBlank() || status.isEmpty()) {
            return false;
        }
        return Arrays.stream(TaskStatus.values()).anyMatch(taskStatus -> status.equalsIgnoreCase(taskStatus.name()));
    }
}
