package dev.sergevas.iot.env.shared.application.service.json;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Composite implements Component {

    private final List<Component> leaves = new ArrayList<>();

    public Composite addLeaf(Component component) {
        leaves.add(component);
        return this;
    }

    @Override
    public String print() {
        return "{" +
                leaves.stream().map(Component::print).collect(Collectors.joining(",")) +
                "}";
    }

    @Override
    public String toString() {
        return print();
    }
}
