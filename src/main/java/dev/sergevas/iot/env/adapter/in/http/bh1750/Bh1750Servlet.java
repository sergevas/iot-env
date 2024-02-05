package dev.sergevas.iot.env.adapter.in.http.bh1750;

import dev.sergevas.iot.env.EnvDeviceAppServiceManager;
import dev.sergevas.iot.env.application.port.in.Bh1750UseCase;
import dev.sergevas.iot.env.application.port.out.SensorException;
import dev.sergevas.iot.env.domain.SensorName;
import dev.sergevas.iot.env.domain.SensorReadingsItemType;
import dev.sergevas.iot.env.domain.SensorType;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

import static dev.sergevas.iot.env.adapter.in.http.SensorErrorTypeMapper.toResponse;
import static dev.sergevas.iot.env.application.service.ExceptionUtils.getStackTrace;

public class Bh1750Servlet extends HttpServlet {

    private Bh1750UseCase bh1750UseCase;

    @Override
    public void init() {
        bh1750UseCase = EnvDeviceAppServiceManager.getInstance().getBh1750UseCase();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Object responseBody;
        try {
            var bh1750Readings = bh1750UseCase.getSensorReadingsItemTypeForBh1750();
            responseBody = new SensorReadingsItemType()
                    .sType(SensorType.LIGHT.name())
                    .sName(SensorName.BH1750.getName())
                    .sTimestamp(bh1750Readings.lightIntensityTimestamp())
                    .sData(String.valueOf(bh1750Readings.lightIntensity()))
                    .toJson();
            System.out.printf("SensorReadingsType with Bh1750 data: %s%n", responseBody);
        } catch (SensorException e) {
            System.err.println(getStackTrace(e));
            responseBody = toResponse(e);
        }
        try (PrintWriter writer = response.getWriter()) {
            response.addHeader("Content-Type", "application/json");
            writer.println(responseBody);
            writer.flush();
        }
    }
}
