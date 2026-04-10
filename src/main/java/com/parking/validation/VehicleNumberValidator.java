package com.parking.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class VehicleNumberValidator implements ConstraintValidator<ValidVehicleNumber, String> {
    private static final String REGEX = "^[A-Z]{2}[0-9]{2}[A-Z]{2}[0-9]{4}$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return false;
        return Pattern.matches(REGEX, value);
    }
}
