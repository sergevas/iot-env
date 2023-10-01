package dev.sergevas.iot.env;

import java.io.InputStream;
import java.util.Properties;

public class EnvDeviceAppConfig {

    public static final String PROPERTIES_FILE_NAME = "application.properties";
    private static final String CONVERT_ERROR_MSG = "Unable to convert the property [%s]=[%s] to %s type";

    private final Properties applicationProperties;

    public EnvDeviceAppConfig() {
        this.applicationProperties = EnvDeviceAppConfig.loadApplicationProperties(PROPERTIES_FILE_NAME);
    }

    public EnvDeviceAppConfig(String propertiesFileName) {
        this.applicationProperties = EnvDeviceAppConfig.loadApplicationProperties(propertiesFileName);
    }

    public int getHttpPort() {
        return fetchIntProperty("http.port");
    }

    public String getHttpApiBasePath() {
        return this.applicationProperties.getProperty("http.api.basePath");
    }

    public boolean isJefI2cEnabled() {
        return fetchBooleanProperty("jef.i2c.enabled");
    }

    public String getJefI2cPath() {
        return this.applicationProperties.getProperty("jef.i2c.path");
    }

    public boolean isJefI2cTenBits() {
        return fetchBooleanProperty("jef.i2c.ten-bits");
    }

    public int getJefI2cRetries() {
        return fetchIntProperty("jef.i2c.retries");
    }

    public int getJefI2cTimeout() {
        return fetchIntProperty("jef.i2c.timeout");
    }

    public int getBh1750ModuleAddress() {
        return fetchIntProperty("bh1750.moduleAddress");
    }

    public int getBme280ModuleAddress() {
        return fetchIntProperty("bme280.moduleAddress");
    }

    public int getBme280ForcedModeTimeout() {
        return fetchIntProperty("bme280.forcedModeTimeout");
    }

    public static Properties loadApplicationProperties(String propertiesFileName) {
        try (InputStream propsInputStream = Thread.currentThread()
                .getContextClassLoader().getResourceAsStream(propertiesFileName)) {
            Properties applicationProperties = new Properties();
            applicationProperties.load(propsInputStream);
            return applicationProperties;
        } catch (Exception e) {
            throw new EnvDeviceAppConfigException("Unable to load [" + propertiesFileName + "] file", e);
        }
    }

    public String getRequiredProperty(String propertyName) {
        var propertyVal = this.applicationProperties.getProperty(propertyName);
        if (propertyVal == null) {
            throw new EnvDeviceAppConfigException(String.format("Unable to fetch property with a key [%s]", propertyName));
        }
        return propertyVal;
    }

    public int fetchIntProperty(String propertyName) {
        var propertyVal = getRequiredProperty(propertyName);
        int intPropertyVal;
        try {
            intPropertyVal = Integer.parseInt(propertyVal);
        } catch (Exception e) {
            throw new EnvDeviceAppConfigException(String.format(CONVERT_ERROR_MSG, propertyName, propertyVal, "int"));
        }
        return intPropertyVal;
    }

    public long fetchLongProperty(String propertyName) {
        var propertyVal = getRequiredProperty(propertyName);
        long longPropertyVal;
        try {
            longPropertyVal = Long.parseLong(propertyVal);
        } catch (Exception e) {
            throw new EnvDeviceAppConfigException(String.format(CONVERT_ERROR_MSG, propertyName, propertyVal, "long"));
        }
        return longPropertyVal;
    }

    public double fetchDoubleProperty(String propertyName) {
        var propertyVal = getRequiredProperty(propertyName);
        double doublePropertyVal;
        try {
            doublePropertyVal = Double.parseDouble(propertyVal);
        } catch (Exception e) {
            throw new EnvDeviceAppConfigException(String.format(CONVERT_ERROR_MSG, propertyName, propertyVal, "double"));
        }
        return doublePropertyVal;
    }

    public boolean fetchBooleanProperty(String propertyName) {
        var propertyVal = getRequiredProperty(propertyName);
        boolean booleanPropertyVal;
        try {
            booleanPropertyVal = Boolean.parseBoolean(propertyVal);
        } catch (Exception e) {
            throw new EnvDeviceAppConfigException(String.format(CONVERT_ERROR_MSG, propertyName, propertyVal, "boolean"));
        }
        return booleanPropertyVal;
    }
}
