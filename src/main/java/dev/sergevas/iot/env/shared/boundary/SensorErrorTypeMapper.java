package dev.sergevas.iot.env.shared.boundary;

import dev.sergevas.iot.env.shared.entity.SensorErrorType;
import dev.sergevas.iot.env.shared.exception.ExceptionUtils;
import dev.sergevas.iot.env.shared.exception.SensorException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Provider
public class SensorErrorTypeMapper implements ExceptionMapper<SensorException> {

    @Override
    public Response toResponse(SensorException se) {
        SensorErrorType sensorError = new SensorErrorType()
                .eventId(se.getEventId())
                .eventName(se.getMessage())
                .eventTimestamp(OffsetDateTime.now(ZoneOffset.UTC))
                .sName(se.getSensorName().getName())
                .sType(se.getSensorType().name())
                .desc(ExceptionUtils.getStackTrace(se.getCause()));
        return Response.serverError().entity(sensorError).build();
    }
}
