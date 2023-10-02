package dev.sergevas.iot.env.shared.adapter.in.web;

import dev.sergevas.iot.env.EnvDeviceAppConfig;
import dev.sergevas.iot.env.EnvDeviceAppServiceManager;
import dev.sergevas.iot.env.bh1750.adapter.in.http.Bh1750Handler;
import dev.sergevas.iot.env.system.adapter.in.http.SystemInfoHandler;
import io.undertow.Undertow;
import io.undertow.UndertowOptions;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.accesslog.AccessLogHandler;
import io.undertow.server.handlers.accesslog.JBossLoggingAccessLogReceiver;
import org.jboss.logging.Logger;

import static io.undertow.Handlers.path;

//@ApplicationPath("/iotenv/api/v1")

public class UndertowLauncher {

    private static final Logger LOG = Logger.getLogger(UndertowLauncher.class);

    public static void startServer() {
        EnvDeviceAppConfig config = EnvDeviceAppServiceManager.getInstance().getEnvDeviceAppConfig();

        final String httpApiSensorsBasePath = config.getHttpApiBasePath() + "/sensors";

        final HttpHandler handlerChain = path()
                .addExactPath(httpApiSensorsBasePath + "/bh1750", new Bh1750Handler())
                .addExactPath(httpApiSensorsBasePath + "/system", new SystemInfoHandler());
        AccessLogHandler accessLogHandler = new AccessLogHandler(handlerChain,
                new JBossLoggingAccessLogReceiver(), "combined", UndertowLauncher.class.getClassLoader());

        Undertow server = Undertow.builder()
                .setServerOption(UndertowOptions.ENABLE_HTTP2, true)
                .addHttpListener(config.getHttpPort(), "0.0.0.0")
                .setHandler(accessLogHandler)
                .build();
        LOG.info("Start server...");
        server.start();
        LOG.info("Start server complete...");
    }
}
