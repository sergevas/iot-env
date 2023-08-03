package dev.sergevas.iot.env.shared.boundary;

import dev.sergevas.iot.env.shared.entity.SensorReadingsItemType;
import dev.sergevas.iot.env.shared.entity.SensorReadingsType;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;


@Path("/sensors")
public interface SensorsApi {

    @GET
    @Path("/bh1750")
    @Produces({"application/json"})
    SensorReadingsItemType getBH1750AmbientLightIntensity();

    @GET
    @Path("/bme280")
    @Produces({"application/json"})
    SensorReadingsType getBME280THP();

    @GET
    @Path("/bmp180")
    @Produces({"application/json"})
    SensorReadingsItemType getBMP180TempPress();

    @GET
    @Path("/sht3x")
    @Produces({"application/json"})
    SensorReadingsItemType getSHT3xHumidTemp();

    @GET
    @Path("/system")
    @Produces({"application/json"})
    SensorReadingsType getSystemData();
}
