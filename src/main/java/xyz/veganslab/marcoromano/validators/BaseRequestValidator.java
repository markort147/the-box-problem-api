package xyz.veganslab.marcoromano.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.annotation.Annotation;

/**
 * Abstract base class for custom validation logic applied to RequestDto fields.
 * This class provides a framework for implementing validation rules for different types of data.
 */
public abstract class BaseRequestValidator<T extends Annotation, V> implements ConstraintValidator<T, V> {

    /**
     * Validates the given value, automatically checking for null.
     * Delegates to the abstract {@code validate} method for specific validation logic.
     *
     * @param value   The value to validate.
     * @param context The validation context.
     * @return {@code true} if the value is valid, {@code false} otherwise.
     */
    @Override
    public boolean isValid(V value, ConstraintValidatorContext context) {
        if (isNull(value, context)) return false;
        return validate(value, context);
    }

    /**
     * Abstract method to be implemented with specific validation logic.
     *
     * @param value   The value to validate.
     * @param context The validation context.
     * @return {@code true} if the value is valid according to the specific logic, {@code false} otherwise.
     */
    protected abstract boolean validate(V value, ConstraintValidatorContext context);

    /**
     * Checks if the provided value is null and adds a constraint violation message if so.
     *
     * @param value   The value to check.
     * @param context The validation context.
     * @return {@code true} if the value is null, {@code false} otherwise.
     */
    protected boolean isNull(V value, ConstraintValidatorContext context) {
        if (value == null) {
            addCustomConstraintViolation(context, "must not be null.");
            return true;
        }
        return false;
    }

    /**
     * Helper method to add a custom constraint violation message.
     *
     * @param context  The validation context.
     * @param template The message template for the violation.
     */
    protected static void addCustomConstraintViolation(ConstraintValidatorContext context, String template) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(template).addConstraintViolation();
    }
}
