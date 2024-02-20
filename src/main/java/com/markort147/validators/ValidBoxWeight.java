package com.markort147.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom validation annotation for ensuring the weight of a box meets specific criteria.
 * It uses {@link BoxWeightValidator} for the actual validation logic.
 * <p>
 * Can be used on any field that represents the weight of a box to enforce
 * constraints such as non-negativity, maximum limit, and decimal precision.
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BoxWeightValidator.class)
public @interface ValidBoxWeight {
    String message() default "Invalid box weight";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
