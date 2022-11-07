package org.parser.json.model;

public enum JsonGrammar {
    CURL_LEFT("{"),
    CURL_RIGHT("}"),
    SQUARE_LEFT("["),
    SQUARE_RIGHT("]"),
    SEMI_COLUMN(":"),
    QUOTE("\""),
    COMMA(",");

    private String literal;

    JsonGrammar(String literal) {
        this.literal = literal;
    }

    public static JsonGrammar valueOf(char c) {
        for (JsonGrammar l : JsonGrammar.values()) {
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