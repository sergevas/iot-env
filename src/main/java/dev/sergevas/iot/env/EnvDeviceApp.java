package dev.sergevas.iot.env;

import cloud.piranha.embedded.EmbeddedPiranha;
import cloud.piranha.embedded.EmbeddedPiranhaBuilder;
import cloud.piranha.http.impl.DefaultHttpServer;
import cloud.piranha.http.webapp.HttpWebApplicationServer;
import dev.sergevas.iot.env.bh1750.adapter.in.http.Bh1750Servlet;
import dev.sergevas.iot.env.system.adapter.in.http.SystemInfoServlet;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.logging.LogManager;

//@ApplicationPath("/iotenv/api/v1")
public class EnvDeviceApp {

    static {
        try (InputStream is = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("logging.properties")) {
            LogManager.getLogManager().readConfiguration(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        EmbeddedPiranha piranha = new EmbeddedPiranhaBuilder()
                .servletsMapped(SystemInfoServlet.class, "/system",
                        Bh1750Servlet.class, "/bh1750")
                .buildAndStart();
        HttpWebApplicationServer webAppServer = new HttpWebApplicationServer();
        webAppServer.addWebApplication(piranha.getWebApplication());
        DefaultHttpServer server = new DefaultHttpServer(8080, webAppServer, false);
        System.out.println(LocalDateTime.now() + " Start server...");
        server.start();
        System.out.println(LocalDateTime.now() + " Start server complete...");
    }
}
