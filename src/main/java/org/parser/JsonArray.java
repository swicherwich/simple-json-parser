package org.parser;

import java.util.ArrayList;
import java.util.List;

public class JsonArray {

    private List<JsonObject> array;
    private List<String> values;

    public JsonArray() {
        this.array = new ArrayList<>();
        this.values = new ArrayList<>();
    }

    public void append(JsonObject object) {
        array.add(object);
    }

    public void append(String value) {
        values.add(value);
    }

    public List<JsonObject> getObjects() {
        return array;
    }

    public List<String> getValues() {
        return values;
    }

}
