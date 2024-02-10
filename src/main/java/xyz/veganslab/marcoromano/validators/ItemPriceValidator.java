package xyz.veganslab.marcoromano.validators;

import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.veganslab.marcoromano.configs.ConstraintsConfiguration;
import xyz.veganslab.marcoromano.configs.DataFormatConfiguration;

import java.math.BigDecimal;

/**
 * Validator for ensuring the price of an item adheres to specified constraints.
 * Validates that the price is non-negative and does not exceed a maximum limit.
 */
public class ItemPriceValidator extends BaseRequestValidator<ValidItemPrice, BigDecimal> {

    private final ConstraintsConfiguration constraintsConfiguration;

    /**
     * Initializes the validator with constraints configuration.
     *
     * @param constraintsConfiguration Configuration providing the maximum price constraint.
     */
    @Autowired
    public ItemPriceValidator(ConstraintsConfiguration constraintsConfiguration) {
        this.constraintsConfiguration = constraintsConfiguration;
    }

    /**
     * Validates the item price against the configured constraints.
     *
     * @param value   The price of the item to be validated.
     * @param context The context in which the constraint is evaluated.
     * @return {@code true} if the price is valid according to the constraints, {@code false} otherwise.
     */
    @Override
    protected boolean validate(BigDecimal value, ConstraintValidatorContext context) {
        boolean isValid = true;
        isValid = isValid && checkPositivity(value, context);
        isValid = isValid && checkAgainstMaxValue(value, context);
        return isValid;
    }

    private static boolean checkPositivity(BigDecimal value, ConstraintValidatorContext context) {
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            addCustomConstraintViolation(context, "invalid item price=" + value + ". Price must be positive.");
            return false;
        }
        return true;
    }

    private boolean checkAgainstMaxValue(BigDecimal value, ConstraintValidatorContext context) {
        BigDecimal configuredMaxPrice = constraintsConfiguration.getItems().getMaxPrice();
        if (value.compareTo(configuredMaxPrice) > 0) {
            addCustomConstraintViolation(context, "invalid item price=" + value + ". Price must be less than " + configuredMaxPrice + ".");
            return false;
        }
        return true;
    }
}