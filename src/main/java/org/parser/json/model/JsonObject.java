package org.parser.json.model;

import java.util.HashMap;
import java.util.Map;

public class JsonObject {

    private Map<String, JsonValue> values;
    private Map<String, JsonObject> objects;
    private Map<String, JsonArray> arrays;

    public JsonObject() {
        this.values = new HashMap<>();
        this.objects = new HashMap<>();
        this.arrays = new HashMap<>();
    }

    public void addValue(String key, JsonValue value) {
        values.put(key, value);
    }

    public void addObject(String key, JsonObject object) {
        objects.put(key, object);
    }

    public void addArray(String key, JsonArray array) {
        arrays.put(key, array);
    }

    public JsonValue getValue(String key) {
        return values.get(key);
    }

    public JsonObject getObject(String key) {
        return objects.get(key);
    }

    public JsonArray getArray(String key) {
        return arrays.get(key);
    }

    public Map<String, JsonValue> getValues() {
        return values;
    }

    public Map<String, JsonArray> getArrays() {
        return arrays;
    }

    public Map<String, JsonObject> getObjects() {
        return objects;
    }
}
