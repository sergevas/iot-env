package dev.sergevas.iot.env.adapter.in.http.bmp180;

import dev.sergevas.iot.env.application.port.in.Bmp180UseCase;
import dev.sergevas.iot.env.domain.SensorReadingsItemType;
import dev.sergevas.iot.env.domain.SensorReadingsType;
import dev.sergevas.iot.env.infra.log.interceptor.Loggable;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.ZoneOffset;

import static dev.sergevas.iot.env.domain.SensorName.BMP180;
import static dev.sergevas.iot.env.domain.SensorType.PRESS;
import static dev.sergevas.iot.env.domain.SensorType.TEMP;
import static java.lang.String.valueOf;
import static java.time.OffsetDateTime.now;

@Path("sensors/bmp180")
public class Bmp180Resource {

    @Inject
    Bmp180UseCase bmp180UseCase;

    @Loggable(logReturnVal = true)
    @GET
    @Path("/readings")
    @Produces(MediaType.APPLICATION_JSON)
    public SensorReadingsType getReadings() {
        var sensorReadings = bmp180UseCase.getSensorReadings();
        return new SensorReadingsType().addSReadingsItem(new SensorReadingsItemType()
                        .sId(valueOf(sensorReadings.chipId()))
                        .sType(TEMP.name())
                        .sName(BMP180.getName())
                        .sTimestamp(now(ZoneOffset.UTC))
                        .sData(valueOf(sensorReadings.temperature())))
                .addSReadingsItem(new SensorReadingsItemType()
                        .sId(valueOf(sensorReadings.chipId()))
                        .sType(PRESS.name())
                        .sName(BMP180.getName())
                        .sTimestamp(now(ZoneOffset.UTC))
                        .sData(valueOf(sensorReadings.pressure())));
    }

    @Loggable(logReturnVal = true)
    @GET
    @Path("/readings/temperature")
    @Produces(MediaType.APPLICATION_JSON)
    public SensorReadingsItemType getTemperature() {
        return new SensorReadingsItemType()
                .sId(bmp180UseCase.getChipId())
                .sType(TEMP.name())
                .sName(BMP180.getName())
                .sTimestamp(now(ZoneOffset.UTC))
                .sData(valueOf(bmp180UseCase.getTemperature()));
    }

    @Loggable(logReturnVal = true)
    @GET
    @Path("/readings/pressure")
    @Produces(MediaType.APPLICATION_JSON)
    public SensorReadingsItemType getPressure() {
        return new SensorReadingsItemType()
                .sId(bmp180UseCase.getChipId())
                .sType(PRESS.name())
                .sName(BMP180.getName())
                .sTimestamp(now(ZoneOffset.UTC))
                .sData(valueOf(bmp180UseCase.getPressure()));
    }

    @Loggable(logReturnVal = true)
    @GET
    @Path("/chipId")
    @Produces(MediaType.APPLICATION_JSON)
    public Bmp180ChipId getChipId() {
        return new Bmp180ChipId(bmp180UseCase.getChipId());
    }

    @Loggable(logReturnVal = true)
    @PUT
    @Path("/reset")
    @Produces(MediaType.APPLICATION_JSON)
    public Response reset() {
        bmp180UseCase.reset();
        return Response.ok().build();
    }
}
