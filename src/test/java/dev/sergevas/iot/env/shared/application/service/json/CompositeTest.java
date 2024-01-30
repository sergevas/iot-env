package dev.sergevas.iot.env.shared.application.service.json;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompositeTest {

    @Test
    void print() {
        var childComposite = new Composite();
        childComposite.addLeaf(new FieldComponent("s_type", ValueComponent.of("LIGHT")))
                .addLeaf(new FieldComponent("s_name", ValueComponent.of("BH1750")))
                .addLeaf(new FieldComponent("s_data", ValueComponent.of("1.67")))
                .addLeaf(new FieldComponent("s_timestamp", ValueComponent.of("2021-08-22T22:44:20.369467Z")));
        var parentComposite = new Composite();
        parentComposite.addLeaf(new ObjectComponent("item", ValueComponent.of(childComposite)));
        assertEquals("", parentComposite.print());
    }
}
