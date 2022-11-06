package org.parser;

public class Main {

    public static void main(String[] args) {
        String json = "{\"names\": [\"Foo\"], \"lastName\": \"Bar\", \"foo\": {\"name\": \"Foo\", \"favoriteBars\": [\"bar\"]}}";
        JsonParser parser = new JsonParser(json);
        JsonObject object = parser.parseJsonObject();
        System.out.println(object.getArray("names").getValues());
        System.out.println(object.getObject("foo").getValue("name"));
        System.out.println(object.getObject("foo").getArray("favoriteBars").getValues());
    }
}
