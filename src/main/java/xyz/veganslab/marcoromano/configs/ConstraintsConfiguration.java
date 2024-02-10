package xyz.veganslab.marcoromano.configs;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

/**
 * Configures constraints for items and boxes from application properties.
 * Maps properties with 'constraint' prefix.
 * <p>
 * Example properties:
 * constraint.items.max-number=15
 * constraint.items.max-weight=100
 * constraint.items.max-price=100
 * constraint.box.max-weight=100
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "constraint")
public class ConstraintsConfiguration {
    private Items items;
    private Box box;

    /**
     * Represents constraints specific to items.
     */
    @Getter
    @Setter
    public static class Items {
        private int maxNumber;
        private BigDecimal maxWeight;
        private BigDecimal maxPrice;
    }

    /**
     * Represents constraints for a box.
     */
    @Getter
    @Setter
    public static class Box {
        private BigDecimal maxWeight;
    }

}
