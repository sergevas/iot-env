package dev.sergevas.iot.env.shared.adapter.in.web;

import dev.sergevas.iot.env.infrastructure.time.DateTimeGen;
import dev.sergevas.iot.env.shared.application.port.out.SensorException;
import dev.sergevas.iot.env.shared.application.service.ExceptionUtils;
import dev.sergevas.iot.env.shared.domain.SensorErrorType;

public class SensorErrorTypeMapper {

    public static SensorErrorType toResponse(SensorException se) {
        return new SensorErrorType()
                .eventId(se.getEventId())
                .eventName(se.getMessage())
                .eventTimestamp(DateTimeGen.now())
                .sName(se.getSensorName().getName())
                .sType(se.getSensorType().name())
                .desc(ExceptionUtils.getStackTrace(se.getCause()));
    }
}
