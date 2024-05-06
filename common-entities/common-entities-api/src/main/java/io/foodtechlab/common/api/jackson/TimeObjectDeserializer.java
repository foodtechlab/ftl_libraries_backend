package io.foodtechlab.common.api.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import io.foodtechlab.common.core.entities.TimeObject;

import java.io.IOException;
import java.time.format.DateTimeParseException;

public class TimeObjectDeserializer extends JsonDeserializer<TimeObject> {
    @Override
    public TimeObject deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        String dateAsString = jsonParser.getText();
        try {
            return TimeObject.parse(dateAsString);
        } catch (
                DateTimeParseException e) {
            throw new IOException(e);
        }
    }
}
