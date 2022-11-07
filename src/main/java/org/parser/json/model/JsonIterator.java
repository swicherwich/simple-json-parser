package org.parser.json.model;

public class JsonIterator {

    private static final char WHITE_SPACE = ' ';

    private char[] chars;
    private int pos = 0;

    public JsonIterator(String json) {
        this.chars = json.toCharArray();
    }

    public char next() {
        if (chars[pos] == WHITE_SPACE) {
            pos++;
            return next();
        }
        return chars[pos++];
    }

    public char peek() {
        return chars[pos + 1];
    }

    public boolean hasNext() {
        return pos != chars.length - 1;
    }
}
