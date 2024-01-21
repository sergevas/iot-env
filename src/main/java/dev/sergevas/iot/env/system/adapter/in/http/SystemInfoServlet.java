package dev.sergevas.iot.env.system.adapter.in.http;

import dev.sergevas.iot.env.EnvDeviceAppServiceManager;
import dev.sergevas.iot.env.shared.entity.SensorName;
import dev.sergevas.iot.env.shared.entity.SensorReadingsItemType;
import dev.sergevas.iot.env.shared.entity.SensorReadingsType;
import dev.sergevas.iot.env.shared.entity.SensorType;
import dev.sergevas.iot.env.system.application.port.in.SystemInfoUseCase;
import dev.sergevas.iot.env.system.domain.SystemInfo;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class SystemInfoServlet extends HttpServlet {

    private SystemInfoUseCase systemInfoUseCase;

    @Override
    public void init() {
        systemInfoUseCase = EnvDeviceAppServiceManager.getInstance().getSystemInfoUseCase();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SystemInfo systemInfo = systemInfoUseCase.getSystemInfo();
        SensorReadingsType sensorReadingsType = new SensorReadingsType()
                .addSReadingsItem(new SensorReadingsItemType()
                        .sType(SensorType.CPU_TEMP.name())
                        .sName(SensorName.ORANGE_PI_ZERO.getName())
                        .sTimestamp(OffsetDateTime.now(ZoneOffset.UTC))
                        .sData(String.valueOf(systemInfo.getCpuTemp())));
        System.out.printf("SensorReadingsType with SystemInfo data: %s", sensorReadingsType);
        try (PrintWriter writer = response.getWriter()) {
            writer.println(sensorReadingsType.toString());
            writer.flush();
        }
    }
}