package com.Sercurity_service.validate;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailValidator.class)

public @interface EmailConstraint {
    String message() default "invalid email address";
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};



}
