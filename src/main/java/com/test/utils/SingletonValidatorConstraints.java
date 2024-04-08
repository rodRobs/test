package com.test.utils;

import com.test.exceptions.BadRequestException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class SingletonValidatorConstraints {

    public static final SingletonValidatorConstraints instance = new SingletonValidatorConstraints();

    public static SingletonValidatorConstraints getInstance() {
        return instance;
    }

    public <T> void validatorConstraints(T object) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> constraintViolations;
        constraintViolations = validator.validate(object);
        StringBuilder errorMessage = new StringBuilder();
        boolean containErrors = false;
        for (ConstraintViolation<T> constraintViolation : constraintViolations) {
            containErrors = true;
            errorMessage.append(constraintViolation.getMessage() + ", ");
        }
        if (errorMessage.length() > 0) {
            errorMessage = new StringBuilder(errorMessage.substring(0, errorMessage.toString().length() - 2));
        }
        if (containErrors)
            throw new BadRequestException(errorMessage.toString());
    }

}
