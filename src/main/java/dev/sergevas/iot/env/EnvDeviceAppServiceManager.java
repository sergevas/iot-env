package dev.sergevas.iot.env;

import dev.sergevas.iot.env.bh1750.adapter.out.i2c.Bh1750Adapter;
import dev.sergevas.iot.env.bh1750.application.port.in.Bh1750UseCase;
import dev.sergevas.iot.env.bh1750.application.port.out.LightIntensity;
import dev.sergevas.iot.env.bh1750.application.service.Bh1750Service;
import dev.sergevas.iot.env.hardware.adapter.i2c.out.I2CAdapter;
import dev.sergevas.iot.env.hardware.port.out.I2C;
import dev.sergevas.iot.env.system.adapter.out.os.CpuTempAdapter;
import dev.sergevas.iot.env.system.application.port.in.SystemInfoUseCase;
import dev.sergevas.iot.env.system.application.port.out.CpuTemp;
import dev.sergevas.iot.env.system.application.service.SystemInfoService;
import io.quarkiverse.jef.java.embedded.framework.linux.i2c.I2CBus;

public class EnvDeviceAppServiceManager {

    private static final EnvDeviceAppServiceManager INSTANCE = EnvDeviceAppServiceManager.init();

    private EnvDeviceAppConfig envDeviceAppConfig;
    private SystemInfoUseCase systemInfoUseCase;
    private Bh1750UseCase bh1750UseCase;

    public EnvDeviceAppConfig getEnvDeviceAppConfig() {
        return envDeviceAppConfig;
    }

    public SystemInfoUseCase getSystemInfoUseCase() {
        return systemInfoUseCase;
    }

    public Bh1750UseCase getBh1750UseCase() {
        return bh1750UseCase;
    }

    public static EnvDeviceAppServiceManager getInstance() {
        return INSTANCE;
    }

    public static EnvDeviceAppServiceManager init() {
//        EnvDeviceAppLoggingConfig.init();
        System.setProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager");
        EnvDeviceAppServiceManager mgr = new EnvDeviceAppServiceManager();
        mgr.envDeviceAppConfig = initEnvDeviceAppConfig();
        mgr.systemInfoUseCase = initSystemInfoUseCase(initCpuTemp());
        mgr.bh1750UseCase = initBh1750UseCase(initLightIntensity(initI2C(mgr.envDeviceAppConfig).getI2CBus(),
                mgr.envDeviceAppConfig));
        return mgr;
    }

    private static EnvDeviceAppConfig initEnvDeviceAppConfig() {
        return new EnvDeviceAppConfig();
    }

    private static CpuTemp initCpuTemp() {
        return new CpuTempAdapter();
    }

    private static SystemInfoUseCase initSystemInfoUseCase(CpuTemp cpuTemp) {
        return new SystemInfoService(cpuTemp);
    }

    private static I2C initI2C(EnvDeviceAppConfig config) {
        return new I2CAdapter(config);
    }

    private static LightIntensity initLightIntensity(I2CBus i2CBus, EnvDeviceAppConfig config) {
        return new Bh1750Adapter(i2CBus, config);
    }

    private static Bh1750UseCase initBh1750UseCase(LightIntensity lightIntensity) {
        return new Bh1750Service(lightIntensity);
    }
}
