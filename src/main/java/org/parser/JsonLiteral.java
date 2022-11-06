package org.parser;

public enum JsonLiteral {
    CURL_LEFT("{"),
    CURL_RIGHT("}"),
    SQUARE_LEFT("["),
    SQUARE_RIGHT("]"),
    SEMI_COLUMN(":"),
    QUOTE("\""),
    COMMA(",");

    private String literal;

    JsonLiteral(String literal) {
        this.literal = literal;
    }

    public static JsonLiteral valueOf(char c) {
        for (JsonLiteral l : JsonLiteral.values()) {
            if (l.literal().equals(String.valueOf(c))) {
                return l;
            }
        }
        return null;
    }

    public String literal() {
        return literal;
    }
}