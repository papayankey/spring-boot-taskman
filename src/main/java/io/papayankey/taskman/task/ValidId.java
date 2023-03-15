package io.papayankey.taskman.task;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;


@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = TaskIdValidator.class)
public @interface ValidId {
    String message() default "id should be an integer";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
