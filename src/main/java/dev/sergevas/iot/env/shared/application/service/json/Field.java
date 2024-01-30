package dev.sergevas.iot.env.shared.application.service.json;

public class Field implements Component {

    private final String name;
    private final Component value;

    public Field(String name, Component value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String print() {
        return "\"" + name + "\":" + "\"" + value.print() + "\"";
    }
}
