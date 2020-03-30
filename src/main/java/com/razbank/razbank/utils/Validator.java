package com.razbank.razbank.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class Validator {
    private static final  Logger logger = LoggerFactory.getLogger(Validator.class);

    public static <T> boolean validate(T object){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        javax.validation.Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> violations = validator.validate(object);

        for (ConstraintViolation<T> violation : violations) {
            logger.error(violation.getMessage());
        }
        return violations.isEmpty();
    }

}
