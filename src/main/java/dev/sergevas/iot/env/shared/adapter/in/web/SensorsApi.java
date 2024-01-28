package dev.sergevas.iot.env.shared.adapter.in.web;

import dev.sergevas.iot.env.shared.domain.SensorReadingsItemType;
import dev.sergevas.iot.env.shared.domain.SensorReadingsType;


//@Path("/sensors")
public interface SensorsApi {

    //    @GET
//    @Path("/bh1750")
//    @Produces({"application/json"})
    SensorReadingsItemType getBH1750AmbientLightIntensity();

    //    @GET
//    @Path("/bme280")
//    @Produces({"application/json"})
    SensorReadingsType getBME280THP();

    //    @GET
//    @Path("/bmp180")
//    @Produces({"application/json"})
    SensorReadingsItemType getBMP180TempPress();

    //    @GET
//    @Path("/sht3x")
//    @Produces({"application/json"})
    SensorReadingsItemType getSHT3xHumidTemp();

    //    @GET
//    @Path("/system")
//    @Produces({"application/json"})
    SensorReadingsType getSystemData();
}
