package dev.sergevas.iot.env.application.service.shared;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtils {

    public static String getStackTrace(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        return sw.toString();
    }
}
