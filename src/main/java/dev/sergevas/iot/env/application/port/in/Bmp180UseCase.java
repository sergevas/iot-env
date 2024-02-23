package dev.sergevas.iot.env.application.port.in;

import dev.sergevas.iot.env.domain.Bmp180Readings;

public interface Bmp180UseCase {

    Bmp180Readings getSensorReadingsItemTypeForBmp180();
}
