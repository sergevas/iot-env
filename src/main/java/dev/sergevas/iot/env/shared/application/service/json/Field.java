package dev.sergevas.iot.env.shared.application.service.json;

import java.util.Optional;

public class Field implements Component {

    private final String name;
    private final Component component;

    public Field(String name, Component value) {
        this.name = name;
        this.component = value;
    }

    @Override
    public String print() {
        return "\"" + name + "\":" + Optional.ofNullable(component.print()).map(c -> "\"" + c + "\"").orElse(null);
    }
}
