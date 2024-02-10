package xyz.veganslab.marcoromano.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom constraint annotation for validating the price of an item.
 * This annotation is used in conjunction with {@link ItemsListValidator} to ensure
 * the size of the list does not exceed a maximum allowed value.
 * <p>
 * Apply this annotation to any field that represents a list of items to validate it against these constraints.
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ItemsListValidator.class)
public @interface ValidItemsList {
    String message() default "Invalid items list";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
