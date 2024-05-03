package io.foodtechlab.common.api.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import io.foodtechlab.common.core.entities.TimeObject;

import java.io.IOException;

public class TimeObjectSerializer extends JsonSerializer<TimeObject> {
    @Override
    public void serialize(TimeObject timeObject, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(timeObject.toString());
    }
}
