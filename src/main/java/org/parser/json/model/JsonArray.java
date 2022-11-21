package org.parser.json.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class JsonArray {

    private List<JsonObject> objects;
    private List<JsonValue> values;

    public JsonArray() {
        this.objects = new ArrayList<>();
        this.values = new ArrayList<>();
    }

    public void append(JsonObject object) {
        objects.add(object);
    }

    public void append(JsonValue value) {
        values.add(value);
    }

    public List<JsonObject> getObjects() {
        return objects;
    }

    public List<JsonValue> getValues() {
        return values;
    }

    public Collection mapToCollection() {
        return values.stream().map(value -> {
            if (value.getType() == JsonType.BOOL) return value.getBool();
            if (value.getType() == JsonType.INTEGER) return value.getInt();
            if (value.getType() == JsonType.STRING) return value.getString();
            throw new RuntimeException(String.format("Unexpected collection element type: %s", value.getType()));
        }).collect(Collectors.toList());
    }

}
