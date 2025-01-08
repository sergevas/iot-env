package dev.sergevas.iot.env.application.service.bmp180;

import dev.sergevas.iot.env.domain.bmp180.PressOversamplingRatio;
import io.quarkus.logging.Log;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class PressOversamplingRatioProvider {

    @ConfigProperty(name = "bmp180.pressureOversamplingRatio")
    String pressureOversamplingRatio;

    private PressOversamplingRatio oversamplingRatio;

    @PostConstruct
    public void init() {
        //  BST-BMP180-DS000-12 Figure 4 Algorithm for pressure and temperature management
        oversamplingRatio = PressOversamplingRatio.valueOf(pressureOversamplingRatio);
        Log.infof("oversamplingRatio=%s", oversamplingRatio);
    }

    public PressOversamplingRatio getRatio() {
        return oversamplingRatio;
    }
}
