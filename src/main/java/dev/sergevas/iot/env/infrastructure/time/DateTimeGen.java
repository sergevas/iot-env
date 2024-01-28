package dev.sergevas.iot.env.infrastructure.time;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class DateTimeGen {

    public static OffsetDateTime now() {
        return OffsetDateTime.now(ZoneOffset.UTC);
    }
}
