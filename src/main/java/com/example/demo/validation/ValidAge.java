package com.example.demo.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AgeValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidAge {

    String message() default "Student must be between 16 and 100 years old and age must be provided";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int min() default 16;

    int max() default 100;
}
