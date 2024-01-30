package dev.sergevas.iot.env.shared.application.service.json;

public class Leaf implements Component {

    private final String name;
    private final Component value;

    public Leaf(String name, Component value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String print() {
        return "\"" + name + "\":" + value.print();
    }
}
