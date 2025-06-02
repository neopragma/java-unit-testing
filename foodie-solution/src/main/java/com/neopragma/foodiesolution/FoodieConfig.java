package com.neopragma.foodiesolution;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FoodieConfig {

    private static Properties props;
    private static final String PROPERTIES_FILENAME = "foodie.properties";

    public static FoodieConfig create() {
        try {
            return create(FoodieConfig.class
                            .getClassLoader()
                            .getResourceAsStream(PROPERTIES_FILENAME));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static FoodieConfig create(InputStream is) {
        try {
            props = new Properties();
            props.load(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new FoodieConfig();
    }

    public String getAsString(String identifier) {
        return props.getProperty(identifier);
    }
}
