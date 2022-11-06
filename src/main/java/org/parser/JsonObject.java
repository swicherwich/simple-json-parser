package org.parser;

import java.util.HashMap;
import java.util.Map;

public class JsonObject {

    private Map<String, String> values;
    private Map<String, JsonObject> objects;
    private Map<String, JsonArray> arrays;

    public JsonObject() {
        this.values = new HashMap<>();
        this.objects = new HashMap<>();
        this.arrays = new HashMap<>();
    }

    public void addValue(String key, String value) {
        values.put(key, value);
    }

    public void addObject(String key, JsonObject object) {
        objects.put(key, object);
    }

    public void addArray(String key, JsonArray array) {
        arrays.put(key, array);
    }

    public String getValue(String key) {
        return values.get(key);
    }

    public JsonObject getObject(String key) {
        return objects.get(key);
    }

    public JsonArray getArray(String key) {
        return arrays.get(key);
    }
}
