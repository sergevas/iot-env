package dev.sergevas.iot.env.application.service.shared;

import java.util.HashMap;
import java.util.Map;

public class Profiler {
    private static final Map<String, Long> points = new HashMap<>();

    public static void init(String aPointName) {
        points.put(aPointName, System.currentTimeMillis());
    }

    public static Long current(String aPointName) {
        return System.currentTimeMillis() - points.get(aPointName);
    }

    public static Long stop(String aPointName) {
        return System.currentTimeMillis() - points.remove(aPointName);
    }

    public static String getCurrentMsg(String aStartPointName, String aCurrentPointName) {
        return "Profile" +
                " [" +
                aCurrentPointName +
                "] " +
                current(aStartPointName);
    }
}
