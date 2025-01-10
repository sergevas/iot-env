package dev.sergevas.iot.env.adapter.in.http.bmp180;

import dev.sergevas.iot.env.application.port.in.bmp180.Bmp180UseCase;
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

import static dev.sergevas.iot.env.application.port.in.bmp180.ToBmp180SensorReadingsTypeMapper.*;

@Path("sensors/bmp180")
public class Bmp180Resource {

    @Inject
    Bmp180UseCase bmp180UseCase;

    @Loggable(logReturnVal = true)
    @GET
    @Path("/readings")
    @Produces(MediaType.APPLICATION_JSON)
    public SensorReadingsType getReadings() {
        return toBmp180SensorReadingsType(bmp180UseCase.getSensorReadings());
    }

    @Loggable(logReturnVal = true)
    @GET
    @Path("/readings/temperature")
    @Produces(MediaType.APPLICATION_JSON)
    public SensorReadingsItemType getTemperature() {
        return toBmp180TempSensorReadingsItemType(bmp180UseCase.getTemperature(), bmp180UseCase.getChipId());
    }

    @Loggable(logReturnVal = true)
    @GET
    @Path("/readings/pressure")
    @Produces(MediaType.APPLICATION_JSON)
    public SensorReadingsType getPressure() {
        return toBmp180PressSensorReadingsItemType(bmp180UseCase.getPressure(), bmp180UseCase.getChipId());
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
