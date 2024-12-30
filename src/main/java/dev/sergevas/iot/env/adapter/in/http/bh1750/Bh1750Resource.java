package dev.sergevas.iot.env.adapter.in.http.bh1750;

import dev.sergevas.iot.env.application.port.in.Bh1750UseCase;
import dev.sergevas.iot.env.domain.SensorName;
import dev.sergevas.iot.env.domain.SensorReadingsItemType;
import dev.sergevas.iot.env.domain.SensorType;
import dev.sergevas.iot.env.infra.log.interceptor.Loggable;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Path("sensors/bh1750")
public class Bh1750Resource {

    @Inject
    Bh1750UseCase bh1750UseCase;

    @Loggable(logReturnVal = true)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public SensorReadingsItemType getBH1750AmbientLightIntensity() {

        return new SensorReadingsItemType()
                .sType(SensorType.LIGHT.name())
                .sName(SensorName.BH1750.getName())
                .sTimestamp(OffsetDateTime.now(ZoneOffset.UTC))
                .sData(String.valueOf(bh1750UseCase.getSensorReadingsItemTypeForBh1750().getLightIntensity()));
    }
}
