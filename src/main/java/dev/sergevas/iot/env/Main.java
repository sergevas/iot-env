
package dev.sergevas.iot.env;

import dev.sergevas.iot.env.shared.boundary.SensorsErrorHandler;
import dev.sergevas.iot.env.shared.boundary.SensorsHttpService;
import dev.sergevas.iot.env.shared.exception.ActuatorException;
import dev.sergevas.iot.env.shared.exception.SensorException;
import dev.sergevas.iot.env.hardware.boundary.SystemInfoHealthCheck;
import dev.sergevas.iot.env.shared.boundary.ActuatorsErrorHandler;
import dev.sergevas.iot.env.shared.boundary.ActuatorsHttpService;
import io.helidon.common.LogConfig;
import io.helidon.config.Config;
import io.helidon.health.HealthSupport;
import io.helidon.health.checks.HealthChecks;
import io.helidon.media.jsonp.JsonpSupport;
import io.helidon.openapi.OpenAPISupport;
import io.helidon.webserver.Routing;
import io.helidon.webserver.WebServer;

public final class Main {

    private Main() {
    }

    /**
     * Application main entry point.
     * @param args command line arguments.
     */
    public static void main(final String[] args) {
        startServer();
    }

    /**
     * Start the server.
     * @return the created {@link WebServer} instance
     */
    static WebServer startServer() {
        LogConfig.configureRuntime();
        Config config = Config.create();

        WebServer server = WebServer.builder(createRouting(config))
                .config(config.get("server"))
                .addMediaSupport(JsonpSupport.create())
                .build();

        server.start()
                .thenAccept(ws -> {
                    System.out.println("GrowLab server is up! http://0.0.0.0:" + ws.port() + "/growlab/api/v1");
                    ws.whenShutdown().thenRun(() -> {
                        Pi4JContextFactory.shutdown();
                        System.out.println("GrowLab server is down.");
                    });
                })
                .exceptionally(t -> {
                    Pi4JContextFactory.shutdown();
                    System.err.println("Startup failed: " + t.getMessage());
                    t.printStackTrace(System.err);
                    return null;
                });
        return server;
    }

    private static Routing createRouting(Config config) {
        HealthSupport health = HealthSupport.builder()
                .addLiveness(HealthChecks.healthChecks())
                .addLiveness(SystemInfoHealthCheck.create())
                .build();

        return Routing.builder()
                .register("/growlab/api/v1", health)
                .register("/growlab/api/v1", OpenAPISupport.create(config.get(OpenAPISupport.Builder.CONFIG_KEY)))
                .register("/growlab/api/v1/sensors", new SensorsHttpService(config))
                .register("/growlab/api/v1/actuators", new ActuatorsHttpService(config))
                .error(SensorException.class, new SensorsErrorHandler())
                .error(ActuatorException.class, new ActuatorsErrorHandler())
                .build();
    }
}
