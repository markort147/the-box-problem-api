package com.markort147.mocks;

import lombok.Getter;
import com.markort147.configs.DataFormatConfiguration;

@Getter
public class DataFormatConfigurationMock extends DataFormatConfiguration {
    private final int weightDecimals = 2;
}
