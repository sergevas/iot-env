package dev.sergevas.iot.env.application.service.json;

public class ObjectField implements Component {

    private final String name;
    private final Component value;

    public ObjectField(String name, Component value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String print() {
        return "\"" + name + "\":" + value.print();
    }
}
