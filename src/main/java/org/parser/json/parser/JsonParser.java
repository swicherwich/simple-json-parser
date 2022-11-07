package org.parser.json.parser;

import org.parser.json.model.JsonArray;
import org.parser.json.model.JsonIterator;
import org.parser.json.model.JsonGrammar;
import org.parser.json.model.JsonObject;

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
                key = parseString();
            } else if (literal == JsonGrammar.SEMI_COLUMN) {
                char c = iterator.next();
                if (JsonGrammar.valueOf(c) == JsonGrammar.QUOTE) {
                    root.addValue(key, parseString());
                } else if (JsonGrammar.valueOf(c) == JsonGrammar.SQUARE_LEFT) {
                    root.addArray(key, parseArray());
                } else if (JsonGrammar.valueOf(c) == JsonGrammar.CURL_LEFT) {
                    root.addObject(key, parseObject());
                }
            } else if (literal == JsonGrammar.CURL_RIGHT) {
                break;
            }
        }

        return root;
    }

    private String parseString() {
        StringBuilder builder = new StringBuilder();

        while (iterator.hasNext()) {
            char c = iterator.next();
            JsonGrammar literal = JsonGrammar.valueOf(c);

            if (literal == JsonGrammar.QUOTE) {
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

            if (JsonGrammar.valueOf(c) == JsonGrammar.QUOTE) {
                jsonArray.append(parseString());
            } else if (JsonGrammar.valueOf(c) == JsonGrammar.CURL_LEFT) {
                jsonArray.append(parseObject());
            } else if (JsonGrammar.valueOf(c) == JsonGrammar.SQUARE_RIGHT) {
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
