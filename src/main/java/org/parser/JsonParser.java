package org.parser;

import static org.parser.JsonLiteral.CURL_LEFT;
import static org.parser.JsonLiteral.CURL_RIGHT;
import static org.parser.JsonLiteral.QUOTE;
import static org.parser.JsonLiteral.SEMI_COLUMN;
import static org.parser.JsonLiteral.SQUARE_LEFT;
import static org.parser.JsonLiteral.SQUARE_RIGHT;

public class JsonParser {

    private JsonObject root;
    private JsonIterator iterator;

    public JsonParser(String json) {
        this.root = new JsonObject();
        this.iterator = new JsonIterator(json);
    }

    public JsonObject parseJsonObject() {
        if (!validate()) {
            throw new RuntimeException();
        }

        String key = null;
        while (iterator.hasNext()) {
            JsonLiteral literal = JsonLiteral.valueOf(iterator.next());

            if (literal == QUOTE) {
                key = parseString();
            } else if (literal == SEMI_COLUMN) {
                char c = iterator.next();
                if (JsonLiteral.valueOf(c) == QUOTE) {
                    root.addValue(key, parseString());
                } else if (JsonLiteral.valueOf(c) == SQUARE_LEFT) {
                    root.addArray(key, parseArray());
                } else if (JsonLiteral.valueOf(c) == CURL_LEFT) {
                    root.addObject(key, parseObject());
                }
            } else if (literal == CURL_RIGHT) {
                break;
            }
        }

        return root;
    }

    private String parseString() {
        StringBuilder builder = new StringBuilder();

        while (iterator.hasNext()) {
            char c = iterator.next();
            JsonLiteral literal = JsonLiteral.valueOf(c);

            if (literal == QUOTE) {
                break;
            }
            builder.append(c);
        }

        return builder.toString();
    }

    private JsonObject parseObject() {
        return parseJsonObject();
    }

    private JsonArray parseArray() {
        JsonArray jsonArray = new JsonArray();

        while (iterator.hasNext()) {
            char c = iterator.next();

            if (JsonLiteral.valueOf(c) == QUOTE) {
                jsonArray.append(parseString());
            } else if (JsonLiteral.valueOf(c) == CURL_LEFT) {
                jsonArray.append(parseObject());
            } else if (JsonLiteral.valueOf(c) == SQUARE_RIGHT) {
                break;
            } else {
                throw new RuntimeException("Unknown array element type: " + c);
            }
        }

        return jsonArray;
    }

    private boolean validate() {
        return true;
    }
}
