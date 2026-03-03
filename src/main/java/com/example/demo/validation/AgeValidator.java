package com.example.demo.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AgeValidator implements ConstraintValidator<ValidAge, Integer> {

    private int min;
    private int max;

    @Override
    public void initialize(ValidAge annotation) {
        this.min = annotation.min();
        this.max = annotation.max();
    }

    @Override
    public boolean isValid(Integer age, ConstraintValidatorContext context) {
        if (age == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Age must not be null")
                    .addConstraintViolation();
            return false;
        }

        if (age < min || age > max) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    String.format("Age %d is not valid. Must be between %d and %d", age, min, max)
            ).addConstraintViolation();
            return false;
        }

        return true;
    }
}
