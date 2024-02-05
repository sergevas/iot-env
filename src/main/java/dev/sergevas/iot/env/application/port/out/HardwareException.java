package dev.sergevas.iot.env.application.port.out;

public class HardwareException extends RuntimeException {

    public HardwareException() {
        super();
    }

    public HardwareException(String message) {
        super(message);
    }

    public HardwareException(String message, Throwable cause) {
        super(message, cause);
    }

    public HardwareException(Throwable cause) {
        super(cause);
    }

    protected HardwareException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
