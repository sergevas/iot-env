package dev.sergevas.iot.env.application.service.json;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ObjectValueTest {

    @Test
    void print() {
        var childObject = new ObjectValue();
        childObject.addField(new Field("s_type", Value.of("LIGHT")))
                .addField(new Field("s_name", Value.of("BH1750")))
                .addField(new Field("s_data", Value.of("1.67")))
                .addField(new Field("s_timestamp", Value.of("2021-08-22T22:44:20.369467Z")));
        var parentObject = new ObjectValue();
        parentObject.addField(new ObjectField("item", childObject));
        assertEquals("""
                {"item":{"s_type":"LIGHT","s_name":"BH1750","s_data":"1.67","s_timestamp":"2021-08-22T22:44:20.369467Z"}}""", parentObject.print());
    }

}
