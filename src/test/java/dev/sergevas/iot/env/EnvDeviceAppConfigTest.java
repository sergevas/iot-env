package dev.sergevas.iot.env;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnvDeviceAppConfigTest {

    private EnvDeviceAppConfig envDeviceAppConfig;

    @BeforeEach
    void setup() {
        try {
            envDeviceAppConfig = new EnvDeviceAppConfig(EnvDeviceAppConfig.PROPERTIES_FILE_NAME);
        } catch (Exception e) {
            throw new RuntimeException("Unable to create EnvDeviceAppConfig instance", e);
        }
    }

    @Test
    void getBh1750ModuleAddress() {
        assertEquals(35, envDeviceAppConfig.getBh1750ModuleAddress());
    }

    @Test
    void getBme280ModuleAddress() {
        assertEquals(118, envDeviceAppConfig.getBme280ModuleAddress());
    }

    @Test
    void getBme280ForcedModeTimeout() {
        assertEquals(2000, envDeviceAppConfig.getBme280ForcedModeTimeout());
    }

    @Test
    void getHttpPort() {
        assertEquals(8080, envDeviceAppConfig.getHttpPort());
    }

    @Test
    void isJefI2cEnabled() {
        assertTrue(envDeviceAppConfig.isJefI2cEnabled());
    }

    @Test
    void getJefI2cPath() {
        assertEquals("/dev/i2c-0", envDeviceAppConfig.getJefI2cPath());
    }

    @Test
    void isJefI2cTenBits() {
        assertTrue(envDeviceAppConfig.isJefI2cTenBits());
    }

    @Test
    void getJefI2cRetries() {
        assertEquals(10, envDeviceAppConfig.getJefI2cRetries());
    }

    @Test
    void getJefI2cTimeout() {
        assertEquals(15, envDeviceAppConfig.getJefI2cTimeout());
    }

    @Test
    void loadApplicationProperties() {
        Exception exception = assertThrows(EnvDeviceAppConfigException.class,
                () -> EnvDeviceAppConfig.loadApplicationProperties(null));
        assertEquals("Unable to load [null] file", exception.getMessage());
        exception = assertThrows(EnvDeviceAppConfigException.class,
                () -> EnvDeviceAppConfig.loadApplicationProperties("file-name"));
        assertEquals("Unable to load [file-name] file", exception.getMessage());
    }

    @Test
    void getRequiredProperty() {
        assertEquals("aStrValue", envDeviceAppConfig.getRequiredProperty("test.stringValueProperty"));
        assertEquals("Unable to fetch property with a key [test]", assertThrows(EnvDeviceAppConfigException.class,
                () -> envDeviceAppConfig.fetchIntProperty("test")).getMessage());
    }

    @Test
    void fetchIntProperty() {
        assertEquals(12345678, envDeviceAppConfig.fetchIntProperty("test.intValueProperty"));
        assertEquals("Unable to convert the property [test.stringValueProperty]=[aStrValue] to int type",
                assertThrows(EnvDeviceAppConfigException.class,
                        () -> envDeviceAppConfig.fetchIntProperty("test.stringValueProperty")).getMessage());
    }

    @Test
    void fetchLongProperty() {
        assertEquals(12345678L, envDeviceAppConfig.fetchLongProperty("test.longValueProperty"));
        assertEquals("Unable to convert the property [test.stringValueProperty]=[aStrValue] to long type",
                assertThrows(EnvDeviceAppConfigException.class,
                        () -> envDeviceAppConfig.fetchLongProperty("test.stringValueProperty")).getMessage());
    }

    @Test
    void fetchDoubleProperty() {
        assertEquals(12345678.987, envDeviceAppConfig.fetchDoubleProperty("test.doubleValueProperty"));
        assertEquals("Unable to convert the property [test.stringValueProperty]=[aStrValue] to double type",
                assertThrows(EnvDeviceAppConfigException.class,
                        () -> envDeviceAppConfig.fetchDoubleProperty("test.stringValueProperty")).getMessage());
    }

    @Test
    void fetchBooleanProperty() {
        assertTrue(envDeviceAppConfig.fetchBooleanProperty("test.booleanValueProperty"));
        assertEquals("Unable to convert the property [test.stringValueProperty]=[aStrValue] to boolean type",
                assertThrows(EnvDeviceAppConfigException.class,
                        () -> envDeviceAppConfig.fetchBooleanProperty("test.stringValueProperty")).getMessage());
    }
}
