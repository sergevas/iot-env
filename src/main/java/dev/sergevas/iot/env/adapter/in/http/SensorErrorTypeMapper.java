package dev.sergevas.iot.env.adapter.in.http;

import dev.sergevas.iot.env.application.port.out.SensorException;
import dev.sergevas.iot.env.application.service.shared.ExceptionUtils;
import dev.sergevas.iot.env.domain.SensorErrorType;
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
