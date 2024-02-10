package xyz.veganslab.marcoromano.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import xyz.veganslab.marcoromano.validators.ValidBoxWeight;
import xyz.veganslab.marcoromano.validators.ValidItemsList;

import java.math.BigDecimal;
import java.util.List;

/**
 * Data Transfer Object representing a request containing items and maximum weight constraint.
 */
@Getter
@Setter
public class RequestDto {
    @JsonProperty("max_weight")
    @ValidBoxWeight
    private BigDecimal maxWeight;

    @JsonProperty("items")
    @NotNull
    @ValidItemsList
    private List<@Valid ItemDto> items;


}
