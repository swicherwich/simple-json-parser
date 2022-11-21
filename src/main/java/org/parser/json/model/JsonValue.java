package org.parser.json.model;

import static org.parser.json.model.JsonType.BOOL;
import static org.parser.json.model.JsonType.INTEGER;

public class JsonValue {

    private final String value;
    private final JsonType type;

    public JsonValue(String value, JsonType type) {
        this.value = value;
        this.type = type;
    }

    public int getInt() {
        if (type == INTEGER) {
            return Integer.parseInt(value);
        }
        throw new RuntimeException(String.format("Incorrect data type: '%s'", value));
    }

    public boolean getBool() {
        if (type == BOOL) {
            return Boolean.parseBoolean(value);
        }
        throw new RuntimeException(String.format("Incorrect data type: '%s'", value));
    }

    public JsonType getType() {
        return type;
    }

    public String getString() {
        return value;
    }
}
