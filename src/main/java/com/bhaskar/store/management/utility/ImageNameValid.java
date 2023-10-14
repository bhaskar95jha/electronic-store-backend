package com.bhaskar.store.management.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ImageNameValid implements ConstraintValidator<ImageNameValidator,String> {
    private Logger log = LoggerFactory.getLogger(ImageNameValidator.class);



    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        log.info("Coustom validator is working for : {}",value);
        if(value.isEmpty())
            return false;
        else
            return true;
    }
}
