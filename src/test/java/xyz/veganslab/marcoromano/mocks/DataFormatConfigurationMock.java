package xyz.veganslab.marcoromano.mocks;

import lombok.Getter;
import xyz.veganslab.marcoromano.configs.DataFormatConfiguration;

@Getter
public class DataFormatConfigurationMock extends DataFormatConfiguration {
    private final int weightDecimals = 2;
}
