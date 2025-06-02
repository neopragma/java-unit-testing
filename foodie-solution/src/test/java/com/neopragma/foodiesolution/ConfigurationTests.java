package com.neopragma.foodiesolution;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

public class ConfigurationTests {

    private FoodieConfig config;

    @BeforeEach
    public void setup() {
        config = FoodieConfig.create(
                new ByteArrayInputStream("base.uri = https://not/real/".getBytes())
        );
    }

    @Test
    public void it_returns_a_defined_configuration_value() {
        assertEquals("https://not/real/", config.getAsString("base.uri"));
    }
    @Test
    public void it_returns_null_when_no_resource_is_defined() {
        assertNull(config.getAsString("undefined"));
    }
}
