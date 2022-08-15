package ua.com.alevel.alexshent;

import ua.com.alevel.alexshent.exception.LoadPropertiesException;
import ua.com.alevel.alexshent.exception.PropertyNullValueException;
import ua.com.alevel.alexshent.exception.ReadConfigurationFileException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Configuration properties reader
 * Implements the Singleton pattern
 */
public class Configuration {
    private static Configuration instance;
    private final Properties properties;
    private static final String CONFIG_FILE_NAME = "config.properties";

    private Configuration() {
        properties = new Properties();
        readProperties(CONFIG_FILE_NAME);
    }

    public static synchronized Configuration getInstance() {
        if (instance == null) {
            instance = new Configuration();
        }
        return instance;
    }

    /**
     * Get property value
     * @param propertyName property name
     * @return property value
     */
    public String getProperty(String propertyName) {
        String propertyValue = properties.getProperty(propertyName);
        if (propertyValue == null) {
            throw new PropertyNullValueException(propertyName + " value is null");
        }
        return propertyValue;
    }

    /**
     * Read all properties from the file
     * @param fileName properties file name
     */
    private void readProperties(String fileName) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (inputStream == null) {
                throw new ReadConfigurationFileException("config file input stream is null");
            }
            properties.load(inputStream);
        } catch (IOException e) {
            throw new LoadPropertiesException("properties load error");
        }
    }
}
