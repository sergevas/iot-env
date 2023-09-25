package dev.sergevas.iot.env;

public class EnvDeviceAppConfigException extends RuntimeException {
    public EnvDeviceAppConfigException() {
    }

    public EnvDeviceAppConfigException(String message) {
        super(message);
    }

    public EnvDeviceAppConfigException(String message, Throwable cause) {
        super(message, cause);
    }

    public EnvDeviceAppConfigException(Throwable cause) {
        super(cause);
    }

    public EnvDeviceAppConfigException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
