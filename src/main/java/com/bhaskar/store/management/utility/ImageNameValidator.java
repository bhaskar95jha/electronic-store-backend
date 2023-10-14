package com.bhaskar.store.management.utility;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ImageNameValid.class)
public @interface ImageNameValidator  {
    //error message
    String message() default "Invalid image name";

    //represent group of constraints
    Class<?>[] groups() default {};

    //additional info about annotation
    Class<? extends Payload>[] payload() default {};
}
