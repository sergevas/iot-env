package dev.sergevas.iot.env;

import dev.sergevas.iot.env.bh1750.boundary.Bh1750Adapter;
import dev.sergevas.iot.env.bh1750.boundary.Bh1750Handler;
import dev.sergevas.iot.env.bh1750.control.Bh1750Service;
import dev.sergevas.iot.env.hardware.adapter.I2CBusProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.web.reactive.HttpHandlerAutoConfiguration;
import org.springframework.boot.autoconfigure.web.reactive.ReactiveWebServerFactoryAutoConfiguration;
import org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration;
import org.springframework.boot.autoconfigure.web.reactive.error.ErrorWebFluxAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

//@SpringBootApplication
public class EnvDeviceApp {

    private final static Class[] autoConfigurationClasses = {
            ReactiveWebServerFactoryAutoConfiguration.class,
            // web
            HttpHandlerAutoConfiguration.class,
            WebFluxAutoConfiguration.class,
            ErrorWebFluxAutoConfiguration.class,
    };

    public static SpringApplication buildApp() {
        return new SpringApplicationBuilder(EnvDeviceApp.class)
                .sources(autoConfigurationClasses)
                .initializers((GenericApplicationContext applicationContext) -> {
                    applicationContext.registerBean(I2CBusProvider.class, I2CBusProvider::new);
                    applicationContext.registerBean(Bh1750Adapter.class,
                            () -> new Bh1750Adapter(applicationContext.getBean(I2CBusProvider.class),
                                    applicationContext.getEnvironment().getProperty("bh1750.moduleAddress", Integer.class)));
                    applicationContext.registerBean(Bh1750Service.class,
                            () -> new Bh1750Service(applicationContext.getBean(Bh1750Adapter.class)));
                    applicationContext.registerBean(Bh1750Handler.class,
                            () -> new Bh1750Handler(applicationContext.getBean(Bh1750Service.class)));

                    applicationContext.registerBean(RouterFunction.class,
                            () -> route(GET("/sensors/bh1750"),
                                    applicationContext.getBean(Bh1750Handler.class)::getSensorReadings));
                }).build();
    }

    public static void main(String[] args) {
        buildApp().run(args);
    }
}
