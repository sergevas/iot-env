package dev.sergevas.iot.env.shared.boundary;

import dev.sergevas.iot.env.shared.entity.SensorErrorType;
import dev.sergevas.iot.env.shared.exception.ExceptionUtils;
import dev.sergevas.iot.env.shared.exception.SensorException;

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
