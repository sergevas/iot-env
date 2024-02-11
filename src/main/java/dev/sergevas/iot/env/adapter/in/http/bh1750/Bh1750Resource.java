package dev.sergevas.iot.env.adapter.in.http.bh1750;

import dev.sergevas.iot.env.application.port.in.Bh1750UseCase;
import dev.sergevas.iot.env.domain.SensorName;
import dev.sergevas.iot.env.domain.SensorReadingsItemType;
import dev.sergevas.iot.env.domain.SensorType;
import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Path("sensors/bh1750")
public class Bh1750Resource {

    private final Bh1750UseCase bh1750UseCase;

    @Inject
    public Bh1750Resource(Bh1750UseCase bh1750UseCase) {
        this.bh1750UseCase = bh1750UseCase;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public SensorReadingsItemType getBH1750AmbientLightIntensity() {
        SensorReadingsItemType sensorReadingsType = new SensorReadingsItemType()
                .sType(SensorType.LIGHT.name())
                .sName(SensorName.BH1750.getName())
                .sTimestamp(OffsetDateTime.now(ZoneOffset.UTC))
                .sData(String.valueOf(bh1750UseCase.getSensorReadingsItemTypeForBh1750().getLightIntensity()));
        Log.info(sensorReadingsType);
        return sensorReadingsType;
    }
}
