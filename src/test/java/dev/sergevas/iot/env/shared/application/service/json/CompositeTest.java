package dev.sergevas.iot.env.shared.application.service.json;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompositeTest {

    @Test
    void print() {
        var childComposite = new Composite();
        childComposite.addLeaf(new Leaf("s_type", Value.of("LIGHT")))
                .addLeaf(new Leaf("s_name", Value.of("BH1750")))
                .addLeaf(new Leaf("s_data", Value.of("1.67")))
                .addLeaf(new Leaf("s_timestamp", Value.of("2021-08-22T22:44:20.369467Z")));
        var parentComposite = new Composite();
        parentComposite.addLeaf(new Leaf("item", Value.of(childComposite)));
        assertEquals("", parentComposite.print());
    }
}
