package xyz.veganslab.marcoromano.utils;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.veganslab.marcoromano.configs.DataFormatConfiguration;

import java.math.BigDecimal;

/**
 * Provides functionality to rescale weight values based on a configuration-driven scale factor.
 * Primarily used for converting weight values to a uniform scale in calculations.
 */
@Log4j2
@Component
public class WeightRescaler {

    private final int scaleFactor;

    /**
     * Constructs a WeightRescaler with a scale factor derived from the DataFormatConfiguration.
     *
     * @param dataFormatConfiguration The configuration providing the scale factor details.
     */
    @Autowired
    public WeightRescaler(DataFormatConfiguration dataFormatConfiguration) {
        scaleFactor = (int) Math.pow(10, dataFormatConfiguration.getWeightDecimals());
        log.info("WeightRescaler initalized. scaleFactor=" + scaleFactor);
    }

    /**
     * Rescales a weight value to a BigDecimal using the configured scale factor.
     *
     * @param originalValue The original weight value as a BigDecimal.
     * @return The rescaled weight as a BigDecimal.
     */
    public BigDecimal rescale(BigDecimal originalValue) {
        return originalValue.multiply(new BigDecimal(scaleFactor));
    }

    /**
     * Converts and rescales a weight value to an integer using the configured scale factor.
     *
     * @param originalValue The original weight value as a BigDecimal.
     * @return The rescaled weight as an integer.
     */
    public int rescaleAsInt(BigDecimal originalValue) {
        return rescale(originalValue).intValue();
    }
}
