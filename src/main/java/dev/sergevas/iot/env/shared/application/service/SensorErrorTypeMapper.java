package dev.sergevas.iot.env.shared.application.service;

import dev.sergevas.iot.env.shared.application.port.out.SensorException;
import dev.sergevas.iot.env.shared.domain.SensorErrorType;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class SensorErrorTypeMapper {

    public SensorErrorType toResponse(SensorException se) {
        return new SensorErrorType()
                .eventId(se.getEventId())
                .eventName(se.getMessage())
                .eventTimestamp(OffsetDateTime.now(ZoneOffset.UTC))
                .sName(se.getSensorName().getName())
                .sType(se.getSensorType().name())
                .desc(ExceptionUtils.getStackTrace(se.getCause()));
    }
}
