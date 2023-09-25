package dev.sergevas.iot.env.bh1750.adapter.in.http;

import dev.sergevas.iot.env.EnvDeviceAppServiceManager;
import dev.sergevas.iot.env.bh1750.application.port.in.Bh1750UseCase;
import dev.sergevas.iot.env.shared.entity.SensorName;
import dev.sergevas.iot.env.shared.entity.SensorReadingsItemType;
import dev.sergevas.iot.env.shared.entity.SensorType;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class Bh1750Servlet extends HttpServlet {

    private Bh1750UseCase bh1750UseCase;

    @Override
    public void init() {
        bh1750UseCase = EnvDeviceAppServiceManager.getInstance().getBh1750UseCase();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SensorReadingsItemType sensorReadingsType = new SensorReadingsItemType()
                .sType(SensorType.LIGHT.name())
                .sName(SensorName.BH1750.getName())
                .sTimestamp(OffsetDateTime.now(ZoneOffset.UTC))
                .sData(String.valueOf(bh1750UseCase.getSensorReadingsItemTypeForBh1750().getLightIntensity()));
        System.out.printf("SensorReadingsType with Bh1750 data: %s", sensorReadingsType);
        try (PrintWriter writer = response.getWriter()) {
            writer.println(sensorReadingsType.toString());
            writer.flush();
        }
    }
}
