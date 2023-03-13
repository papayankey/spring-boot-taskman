package io.papayankey.taskman.task;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = TaskStatusValidator.class)
public @interface ValidTaskStatus {
    String message() default "status must be one of {inactive, active, completed}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
