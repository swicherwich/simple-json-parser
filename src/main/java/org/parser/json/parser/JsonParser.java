package org.parser.json.parser;

import org.parser.json.model.JsonArray;
import org.parser.json.model.JsonGrammar;
import org.parser.json.model.JsonIterator;
import org.parser.json.model.JsonObject;
import org.parser.json.model.JsonType;
import org.parser.json.model.JsonValue;

public class JsonParser {

    private JsonIterator iterator;

    public JsonParser(String json) {
        this.iterator = new JsonIterator(json);
    }

    public JsonObject parseJsonObject() {
        if (!validate()) {
            throw new RuntimeException();
        }

        JsonObject root = new JsonObject();

        String key = null;
        while (iterator.hasNext()) {
            JsonGrammar literal = JsonGrammar.valueOf(iterator.next());

            if (literal == JsonGrammar.QUOTE) {
                key = parseValue();
            } else if (literal == JsonGrammar.SEMI_COLUMN) {
                char c = iterator.peek();
                JsonGrammar j = JsonGrammar.valueOf(c);
                 if (j == JsonGrammar.SQUARE_LEFT) {
                    iterator.next(); // skip [ character for parsing string starting from first letter
                    root.addArray(key, parseArray());
                } else if (j == JsonGrammar.CURL_LEFT) {
                    iterator.next(); // skip quote character for parsing string starting from first letter
                    root.addObject(key, parseObject());
                } else {
                    String value = parseValue();
                    JsonType type = JsonType.getType(value);
                    root.addValue(key, new JsonValue(value, type));
                }
            } else if (literal == JsonGrammar.CURL_RIGHT) {
                break;
            }
        }

        return root;
    }

    private String parseValue() {
        if (JsonGrammar.valueOf(iterator.peek()) == JsonGrammar.QUOTE) {
            iterator.next();
        }

        StringBuilder builder = new StringBuilder();

        while (iterator.hasNext()) {
            char c = iterator.next();
            JsonGrammar literal = JsonGrammar.valueOf(c);

            if (literal == JsonGrammar.QUOTE
                || literal == JsonGrammar.COMMA
                || literal == JsonGrammar.CURL_RIGHT
                || literal == JsonGrammar.SQUARE_RIGHT) {
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
            char c = iterator.peek();

            if (JsonGrammar.valueOf(c) == JsonGrammar.SQUARE_RIGHT) {
                break;
            } else if (JsonGrammar.valueOf(c) == JsonGrammar.CURL_LEFT) {
                jsonArray.append(parseObject());
            } else if (JsonGrammar.valueOf(c) == JsonGrammar.QUOTE) {
                String value = parseValue();
                jsonArray.append(new JsonValue(value, JsonType.getType(value)));
            } else {
                String value = parseValue();
                jsonArray.append(new JsonValue(value, JsonType.getType(value)));
            }
        }

        return jsonArray;
    }

    private boolean validate() {
        return true;
    }
}
