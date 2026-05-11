package com.recipeshare.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static final Properties PROPS = new Properties();
    private static final ConfigReader INSTANCE = new ConfigReader();

    private ConfigReader() {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (is == null) throw new RuntimeException("config.properties no encontrado en src/test/resources");
            PROPS.load(is);
        } catch (IOException e) {
            throw new RuntimeException("Error cargando config.properties", e);
        }
    }

    public static String get(String key) {
        String value = PROPS.getProperty(key);
        if (value == null || value.isBlank()) throw new RuntimeException("Propiedad no definida: " + key);
        return value;
    }

    public static String getBaseUrl()     { return get("base.url"); }
    public static String getAuthUrl()     { return get("auth.url"); }
    public static String getEmail()       { return get("email.valido"); }
    public static String getPassword()    { return get("password.valido"); }
    public static String getRecipeId()    { return get("recipe.id.existente"); }
}