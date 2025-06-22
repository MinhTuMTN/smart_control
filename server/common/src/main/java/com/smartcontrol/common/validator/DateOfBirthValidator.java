package com.smartcontrol.common.validator;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateOfBirthValidator implements ConstraintValidator<DateOfBirth, Date> {

    private int minAge;

    @Override
    public void initialize(DateOfBirth constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        minAge = constraintAnnotation.minAge();
    }

    @Override
    public boolean isValid(Date value, ConstraintValidatorContext context) {
        // If value is null, check it with @NotNull annotation
        if (value == null) {
            return true;
        }
        
        Period period = Period.between(
            value.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
            LocalDate.now()
        );

        if (period.getYears() < minAge) {
            // Commented for reference, can be used to customize the error message
            // context.disableDefaultConstraintViolation();
            // context.buildConstraintViolationWithTemplate(
            //     String.format("You must be at least %d years old", minAge)
            // ).addConstraintViolation();
            return false;
        }

        return true;
    }
    
}
