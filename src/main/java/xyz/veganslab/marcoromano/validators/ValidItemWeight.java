package xyz.veganslab.marcoromano.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom constraint annotation for validating the weight of an item.
 * When applied to a field, it uses the {@link ItemWeightValidator} to ensure
 * that the item's weight adheres to specific constraints such as non-negativity,
 * adherence to a maximum limit, and compliance with decimal precision rules.
 * <p>
 * This annotation should be placed on fields representing the weight of an item to validate
 * against the prescribed constraints.
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ItemWeightValidator.class)
public @interface ValidItemWeight {
    String message() default "Invalid item weight";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
