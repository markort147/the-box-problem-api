package xyz.veganslab.marcoromano.validators;

import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.veganslab.marcoromano.configs.ConstraintsConfiguration;
import xyz.veganslab.marcoromano.models.ItemDto;

import java.math.BigDecimal;
import java.util.List;

/**
 * Validator for checking the size of request items list.
 * Ensures the size is within a maximum limit.
 */
public class ItemsListValidator extends BaseRequestValidator<ValidItemsList, List<ItemDto>> {

    private final ConstraintsConfiguration constraintsConfiguration;

    /**
     * Constructs a validator with configuration settings for constraints and data format.
     *
     * @param constraintsConfiguration Configuration providing maximum size constraint.
     */
    @Autowired
    public ItemsListValidator(ConstraintsConfiguration constraintsConfiguration) {
        this.constraintsConfiguration = constraintsConfiguration;
    }

    /**
     * Validates the items list against the specified constraints.
     *
     * @param value   The items list to be validated.
     * @param context The context in which the constraint is evaluated.
     * @return {@code true} if the list is valid according to the constraints, {@code false} otherwise.
     */
    @Override
    protected boolean validate(List<ItemDto> value, ConstraintValidatorContext context) {
        boolean isValid = true;
        isValid = isValid && checkSize(value, context);
        return isValid;
    }

    private boolean checkSize(List<ItemDto> value, ConstraintValidatorContext context) {
        int maxNumber = constraintsConfiguration.getItems().getMaxNumber();
        if (value.size() > maxNumber) {
            addCustomConstraintViolation(context, "invalid number of items=" + value.size() + ". Number of items must be within " + maxNumber + ".");
            return false;
        }
        return true;
    }
}