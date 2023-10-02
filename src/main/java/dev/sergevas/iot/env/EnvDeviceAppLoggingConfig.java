package dev.sergevas.iot.env;

import java.io.InputStream;
import java.util.logging.LogManager;

public class EnvDeviceAppLoggingConfig {

    private static final String LOGGING_PROPERTIES = "logging.properties";

    public static void init() {
        init(LOGGING_PROPERTIES);
    }

    public static void init(String logPropsFileName) {
        try (InputStream logConfigInputStream = Thread.currentThread()
                .getContextClassLoader().getResourceAsStream(logPropsFileName)) {
            LogManager.getLogManager().readConfiguration(logConfigInputStream);
        } catch (Exception e) {
            throw new EnvDeviceAppConfigException("Unable to load [" + logPropsFileName + "] file", e);
        }
    }
}
