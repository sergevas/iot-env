package dev.sergevas.iot.env.application.port.in.bh1750;

import dev.sergevas.iot.env.domain.Bh1750Readings;

public interface Bh1750UseCase {

    Bh1750Readings getSensorReadingsItemTypeForBh1750();
}
