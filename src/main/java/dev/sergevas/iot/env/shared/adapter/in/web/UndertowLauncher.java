package dev.sergevas.iot.env.shared.adapter.in.web;

import dev.sergevas.iot.env.EnvDeviceAppConfig;
import dev.sergevas.iot.env.EnvDeviceAppServiceManager;
import dev.sergevas.iot.env.bh1750.adapter.in.http.Bh1750Servlet;
import dev.sergevas.iot.env.system.adapter.in.http.SystemInfoServlet;
import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.UndertowOptions;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.PathHandler;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import jakarta.servlet.ServletException;
import org.jboss.logging.Logger;

import static io.undertow.servlet.Servlets.*;

//@ApplicationPath("/iotenv/api/v1")

public class UndertowLauncher {

    private static final Logger LOG = Logger.getLogger(UndertowLauncher.class);

    public static void startServer() {
        final EnvDeviceAppConfig config = EnvDeviceAppServiceManager.getInstance().getEnvDeviceAppConfig();
        final String httpApiBasePath = config.getHttpApiBasePath();
        try {
            DeploymentInfo servletBuilder = deployment()
                    .setClassLoader(UndertowLauncher.class.getClassLoader())
                    .setContextPath(httpApiBasePath)
                    .setDeploymentName("iot-env.war")
                    .addServlets(
                            servlet("Bh1750Servlet", Bh1750Servlet.class)
                                    .addMapping("/sensors/bh1750"),
                            servlet("SystemInfoServlet", SystemInfoServlet.class)
                                    .addMapping("sensors/system"));
            DeploymentManager manager = defaultContainer().addDeployment(servletBuilder);
            manager.deploy();
            HttpHandler servletHandler = manager.start();
            PathHandler path = Handlers.path(Handlers.redirect(httpApiBasePath))
                    .addPrefixPath(httpApiBasePath, servletHandler);
            Undertow server = Undertow.builder()
                    .setServerOption(UndertowOptions.ENABLE_HTTP2, true)
                    .addHttpListener(config.getHttpPort(), "0.0.0.0")
                    .setHandler(path)
                    .build();
            LOG.info("Start server...");
            server.start();
            LOG.info("Start server complete...");
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }
}
