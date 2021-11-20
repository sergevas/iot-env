package dev.sergevas.iot.env.shared.controller;

import io.helidon.config.Config;

import java.util.Map;

public class HelidonConfigHandler {

    public static Map<String, String> getConfigMap(Config config, String rootNodeName) {
        return config.get(rootNodeName).detach().asMap().get();
    }
}
