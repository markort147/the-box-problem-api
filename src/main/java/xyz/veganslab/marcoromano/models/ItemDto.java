package xyz.veganslab.marcoromano.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import xyz.veganslab.marcoromano.validators.ValidItemPrice;
import xyz.veganslab.marcoromano.validators.ValidItemWeight;


import java.math.BigDecimal;


/**
 * Data Transfer Object representing an item.
 * <p>
 * This class holds information about an item, including its ID, weight, and price.
 */
@Getter
@Setter
public class ItemDto {
    @JsonProperty("Item ID")
    @NotNull
    private Integer id;

    @JsonProperty("Weight")
    @ValidItemWeight
    private BigDecimal weight;

    @JsonProperty("Price")
    @ValidItemPrice
    private BigDecimal price;

    @Override
    public String toString() {
        return "(id:" + id + ", weight:" + weight + ", price:" + price + ")";
    }
}