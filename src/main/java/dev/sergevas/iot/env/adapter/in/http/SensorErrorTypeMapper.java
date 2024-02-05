package dev.sergevas.iot.env.adapter.in.http;

import dev.sergevas.iot.env.application.service.DateTimeGen;
import dev.sergevas.iot.env.application.port.out.SensorException;
import dev.sergevas.iot.env.application.service.ExceptionUtils;
import dev.sergevas.iot.env.domain.SensorErrorType;

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
