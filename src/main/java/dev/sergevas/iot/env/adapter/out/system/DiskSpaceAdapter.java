package dev.sergevas.iot.env.adapter.out.system;

import dev.sergevas.iot.env.application.port.out.HardwareException;
import dev.sergevas.iot.env.application.port.out.SensorException;
import dev.sergevas.iot.env.application.port.out.health.DiskSpaceFetcher;
import dev.sergevas.iot.env.domain.SensorName;
import dev.sergevas.iot.env.domain.SensorType;
import dev.sergevas.iot.env.domain.health.DiskSpace;
import dev.sergevas.iot.env.infra.log.interceptor.Loggable;
import io.quarkus.logging.Log;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Paths;

import static dev.sergevas.iot.env.domain.ErrorEvent.E_SYSTEM_0002;

/**
 * Some chunks of the code is from
 * io\helidon\health\helidon-health-checks\2.6.4\helidon-health-checks-2.6.4-sources.jar!\io\helidon\health\checks\DiskSpaceHealthCheck.java
 */

@ApplicationScoped
public class DiskSpaceAdapter implements DiskSpaceFetcher {

    @ConfigProperty(name = "healthcheck.diskSpace.path", defaultValue = ".")
    String diskSpacePath;
    private FileStore fileStore;

    @PostConstruct
    public void init() {
        try {
            fileStore = Files.getFileStore(Paths.get(diskSpacePath));
        } catch (IOException ioe) {
            String errorMsgFormatted = "Unable to get FileStore located at %s";
            Log.errorf(ioe, errorMsgFormatted, diskSpacePath);
            throw new HardwareException(String.format(errorMsgFormatted, diskSpacePath), ioe);
        }
    }

    @Loggable(logReturnVal = true)
    @Override
    public DiskSpace getDiskSpace() {
        try {
            return new DiskSpace(fileStore.getUsableSpace(), fileStore.getTotalSpace());
        } catch (IOException ioe) {
            throw new SensorException(E_SYSTEM_0002.getId(), SensorType.DISK_SPACE, SensorName.RASPBERRY_PI_ZERO_2, E_SYSTEM_0002.getName(), ioe);
        }
    }
}
