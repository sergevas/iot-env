package dev.sergevas.iot.env.bh1750.application.port.in;

import dev.sergevas.iot.env.bh1750.domain.Bh1750Readings;

public interface Bh1750UseCase {

    Bh1750Readings getSensorReadingsItemTypeForBh1750();
}
