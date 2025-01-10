package dev.sergevas.iot.env.adapter.in.http.sht3x;

import dev.sergevas.iot.env.application.port.in.sht3x.Sht3xUseCase;
import dev.sergevas.iot.env.application.port.in.sht3x.ToSht3xSensorReadingsTypeMapper;
import dev.sergevas.iot.env.domain.SensorReadingsType;
import dev.sergevas.iot.env.domain.sht3x.HeaterState;
import dev.sergevas.iot.env.infra.log.interceptor.Loggable;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("sensors/sht3x")
public class Sht3xResource {

    @Inject
    Sht3xUseCase sht3xUseCase;

    @Loggable(logReturnVal = true)
    @GET
    @Path("/readings")
    @Produces(MediaType.APPLICATION_JSON)
    public SensorReadingsType getReadings() {
        return ToSht3xSensorReadingsTypeMapper.toSht3xSensorReadingsType(sht3xUseCase.getSensorReadings());
    }

    @Loggable(logReturnVal = true)
    @GET
    @Path("/status")
    @Produces(MediaType.APPLICATION_JSON)
    public Sht3xStatus getStatus() {
        return Sht3xStatus.toSensorStatus(sht3xUseCase.getStatus());
    }

    @Loggable(logReturnVal = true)
    @PUT
    @Path("/status")
    @Produces(MediaType.APPLICATION_JSON)
    public Sht3xStatus clearStatus() {
        return Sht3xStatus.toSensorStatus(sht3xUseCase.clearStatus());
    }

    @Loggable(logReturnVal = true)
    @PUT
    @Path("/heater")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public HeaterUpdateResult updateHeaterState(HeaterNewState heaterNewState) {
        return new HeaterUpdateResult(sht3xUseCase
                .updateHeaterState(HeaterState.valueOf(heaterNewState.state().name())));
    }

    @Loggable(logReturnVal = true)
    @PUT
    @Path("/reset")
    @Produces(MediaType.APPLICATION_JSON)
    public Response reset() {
        sht3xUseCase.reset();
        return Response.ok().build();
    }
}
