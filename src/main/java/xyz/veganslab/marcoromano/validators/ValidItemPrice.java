package xyz.veganslab.marcoromano.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom constraint annotation for validating the price of an item.
 * This annotation is used in conjunction with {@link ItemPriceValidator} to enforce rules
 * such as ensuring the price is not null, is positive, and does not exceed a maximum allowed value.
 * <p>
 * Apply this annotation to any field that represents the price of an item to validate it against these constraints.
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ItemPriceValidator.class)
public @interface ValidItemPrice {
    String message() default "Invalid item price";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
