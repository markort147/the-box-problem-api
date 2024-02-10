package xyz.veganslab.marcoromano.validators;

import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.veganslab.marcoromano.configs.ConstraintsConfiguration;
import xyz.veganslab.marcoromano.configs.DataFormatConfiguration;

import java.math.BigDecimal;

/**
 * Validator for checking the weight of an item against specific constraints.
 * Ensures the weight is non-negative, within a maximum limit, and adheres to decimal precision rules.
 */
public class ItemWeightValidator extends BaseRequestValidator<ValidItemWeight, BigDecimal> {

    private final ConstraintsConfiguration constraintsConfiguration;
    private final DataFormatConfiguration dataFormatConfiguration;

    /**
     * Constructs a validator with configuration settings for constraints and data format.
     *
     * @param constraintsConfiguration Configuration providing maximum weight constraint.
     * @param dataFormatConfiguration   Configuration for weight decimal precision.
     */
    @Autowired
    public ItemWeightValidator(ConstraintsConfiguration constraintsConfiguration, DataFormatConfiguration dataFormatConfiguration) {
        this.constraintsConfiguration = constraintsConfiguration;
        this.dataFormatConfiguration = dataFormatConfiguration;
    }

    /**
     * Validates the item weight against the specified constraints.
     *
     * @param value   The weight of the item to be validated.
     * @param context The context in which the constraint is evaluated.
     * @return {@code true} if the weight is valid according to the constraints, {@code false} otherwise.
     */
    @Override
    protected boolean validate(BigDecimal value, ConstraintValidatorContext context) {
        boolean isValid = true;
        isValid = isValid && checkNumberOfDecimals(value, context);
        isValid = isValid && checkPositivity(value, context);
        isValid = isValid && checkAgainstMaxValue(value, context);
        return isValid;
    }

    private boolean checkNumberOfDecimals(BigDecimal value, ConstraintValidatorContext context) {
        int configuredDecimals = dataFormatConfiguration.getWeightDecimals();
        if (value.scale() > configuredDecimals) {
            addCustomConstraintViolation(context, "invalid item weight=" + value + ". Weight must have at most " + configuredDecimals + " decimals.");
            return false;
        }
        return true;
    }

    private static boolean checkPositivity(BigDecimal value, ConstraintValidatorContext context) {
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            addCustomConstraintViolation(context, "invalid item weight=" + value + ". Weight must be positive.");
            return false;
        }
        return true;
    }

    private boolean checkAgainstMaxValue(BigDecimal value, ConstraintValidatorContext context) {
        BigDecimal configuredMaxWeight = constraintsConfiguration.getItems().getMaxWeight();
        if (value.compareTo(configuredMaxWeight) > 0) {
            addCustomConstraintViolation(context, "invalid item weight=" + value + ". Weight must be less than " + configuredMaxWeight + ".");
            return false;
        }
        return true;
    }
}