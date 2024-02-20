package com.markort147.validators;

import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import com.markort147.configs.ConstraintsConfiguration;
import com.markort147.configs.DataFormatConfiguration;

import java.math.BigDecimal;

/**
 * Validator for checking the maximum weight of a box against specific constraints.
 * Ensures the weight value is non-negative, does not exceed a maximum limit, and adheres to specified decimal precision.
 */
public class BoxWeightValidator extends BaseRequestValidator<ValidBoxWeight, BigDecimal> {

    private final ConstraintsConfiguration constraintsConfiguration;
    private final DataFormatConfiguration dataFormatConfiguration;

    /**
     * Initializes the validator with configuration settings for constraints and data format.
     *
     * @param constraintsConfiguration Configuration for constraint values.
     * @param dataFormatConfiguration   Configuration for data formatting rules.
     */
    @Autowired
    public BoxWeightValidator(ConstraintsConfiguration constraintsConfiguration, DataFormatConfiguration dataFormatConfiguration) {
        this.constraintsConfiguration = constraintsConfiguration;
        this.dataFormatConfiguration = dataFormatConfiguration;
    }

    /**
     * Performs validation on the box weight value.
     *
     * @param value   The weight of the box to be validated.
     * @param context Context in which the constraint is evaluated.
     * @return {@code true} if the weight value passes all validation checks, {@code false} otherwise.
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
            addCustomConstraintViolation(context, "invalid box weight=" + value + ". Weight must have at most " + configuredDecimals + " decimals.");
            return false;
        }
        return true;
    }

    private static boolean checkPositivity(BigDecimal value, ConstraintValidatorContext context) {
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            addCustomConstraintViolation(context, "invalid box weight=" + value + ". Weight must be positive.");
            return false;
        }
        return true;
    }

    private boolean checkAgainstMaxValue(BigDecimal value, ConstraintValidatorContext context) {
        BigDecimal configuredMaxWeight = constraintsConfiguration.getBox().getMaxWeight();
        if (value.compareTo(configuredMaxWeight) > 0) {
            addCustomConstraintViolation(context, "invalid box weight=" + value + ". Weight must be less than " + configuredMaxWeight + ".");
            return false;
        }
        return true;
    }
}