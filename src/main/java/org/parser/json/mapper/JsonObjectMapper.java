package org.parser.json.mapper;

import org.parser.json.model.JsonArray;
import org.parser.json.model.JsonObject;
import org.parser.json.parser.JsonParser;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class JsonObjectMapper {

    public <T> T map(String json, Class<T> clazz) {
        try {
            JsonParser parser = new JsonParser(json);
            JsonObject jsonObject = parser.parseJsonObject();
            T obj = createClass(clazz);
            initClass(jsonObject, obj);
            return obj;
        } catch (NoSuchFieldException | InvocationTargetException | IllegalAccessException | InstantiationException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private  <T> T createClass(Class<?> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return (T) clazz.getDeclaredConstructor().newInstance();
    }

    private  <T> void initClass(JsonObject jsonObject, T obj) throws NoSuchFieldException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        for (Map.Entry<String, String> value : jsonObject.getValues().entrySet()) {
            Field field = obj.getClass().getDeclaredField(value.getKey());
            field.setAccessible(true);
            field.set(obj, value.getValue());
        }

        for (Map.Entry<String, JsonArray> array : jsonObject.getArrays().entrySet()) {
            Field field = obj.getClass().getDeclaredField(array.getKey());
            field.setAccessible(true);
            field.set(obj, array.getValue().getValues());
        }

        for (Map.Entry<String, JsonObject> object : jsonObject.getObjects().entrySet()) {
            Field field = obj.getClass().getDeclaredField(object.getKey());
            field.setAccessible(true);

            T t = createClass(field.getType());
            initClass(object.getValue(), t);

            field.set(obj, t);
        }
    }
}
