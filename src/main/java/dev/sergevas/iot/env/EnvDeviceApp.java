package dev.sergevas.iot.env;


import dev.sergevas.iot.env.shared.adapter.in.web.UndertowLauncher;

public class EnvDeviceApp {

    public static void main(String[] args) {
        UndertowLauncher.startServer();
    }
}
