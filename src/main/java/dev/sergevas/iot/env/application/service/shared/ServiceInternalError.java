package dev.sergevas.iot.env.application.service.shared;

public class ServiceInternalError extends RuntimeException {

    public ServiceInternalError(String message) {
        super(message);
    }
}
