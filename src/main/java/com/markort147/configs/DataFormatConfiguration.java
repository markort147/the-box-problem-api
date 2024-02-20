package com.markort147.configs;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


/**
 * Binds data format properties from the application configuration.
 * Properties with the 'data_format' prefix are mapped to this class.
 * <p>
 * Example property: data_format.weightDecimal=2
 */
@Log4j2
@Getter
@Configuration
@ConfigurationProperties(prefix = "data-format")
public class DataFormatConfiguration {
    private int weightDecimals;

    public void setWeightDecimals(int weightDecimals) {
        this.weightDecimals = weightDecimals;
        log.info("Loaded property: weightDecimal=" + weightDecimals);
    }
}
