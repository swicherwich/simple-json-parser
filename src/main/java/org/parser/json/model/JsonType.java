package org.parser.json.model;

import org.apache.commons.lang3.StringUtils;

public enum JsonType {
    STRING,
    INTEGER,
    BOOL;

    public static JsonType getType(String value) {
        if (value.equals("true") || value.equals("false")) {
            return BOOL;
        }
        if (StringUtils.isNumeric(value)) {
            return INTEGER;
        }
        return STRING;
    }
}
