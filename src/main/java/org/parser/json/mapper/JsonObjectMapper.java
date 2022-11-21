package org.parser.json.mapper;

import org.parser.json.model.JsonArray;
import org.parser.json.model.JsonObject;
import org.parser.json.model.JsonValue;
import org.parser.json.parser.JsonParser;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
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
        for (Map.Entry<String, JsonValue> value : jsonObject.getValues().entrySet()) {
            Field field = obj.getClass().getDeclaredField(value.getKey());
            field.setAccessible(true);
            resolveTypeAndSetValue(obj, field, value.getValue());
        }

        for (Map.Entry<String, JsonArray> array : jsonObject.getArrays().entrySet()) {
            JsonArray jsonArray = array.getValue();

            Field field = obj.getClass().getDeclaredField(array.getKey());
            field.setAccessible(true);
            field.set(obj, jsonArray.mapToCollection());
        }

        for (Map.Entry<String, JsonObject> object : jsonObject.getObjects().entrySet()) {
            Field field = obj.getClass().getDeclaredField(object.getKey());
            field.setAccessible(true);

            T t = createClass(field.getType());
            initClass(object.getValue(), t);

            field.set(obj, t);
        }
    }

    private <T> void resolveTypeAndSetValue(T obj, Field field, JsonValue value) throws IllegalAccessException {
        Class<?> fieldType = field.getType();

        if (fieldType.isPrimitive()) {
            if (Integer.TYPE.equals(fieldType)) {
                field.setInt(obj, value.getInt());
            } else if (Boolean.TYPE.equals(fieldType)) {
                field.setBoolean(obj, value.getBool());
            }
        } else if (fieldType.equals(String.class)) {
            field.set(obj, value.getString());
        } else if (fieldType.equals(Boolean.class)) {
            field.set(obj, value.getBool());
        } else if (fieldType.equals(Integer.class)) {
            field.set(obj, value.getInt());
        }
    }
}
