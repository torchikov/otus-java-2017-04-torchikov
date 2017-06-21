package ru.torchikov.jdbc;

import java.lang.ref.SoftReference;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sergei on 17.06.17.
 *
 */
public final class ReflectionHelper {
    private ReflectionHelper() {
    }

    private static Map<Class<?>, SoftReference<Map<String, Field>>> cache = new HashMap<>();

    static void setFieldValue(String fieldName, Object value, Object target) throws NoSuchFieldException, IllegalAccessException {
        Field field = getField(fieldName, target.getClass());
        boolean isAccessible = field.isAccessible();
        field.setAccessible(true);
        field.set(target, value);
        field.setAccessible(isAccessible);
    }

    public static Object getFieldValue(String fieldName, Object target) throws NoSuchFieldException, IllegalAccessException {
        Field field = getField(fieldName, target.getClass());
        boolean isAccessible = field.isAccessible();
        field.setAccessible(true);
        Object result = field.get(target);
        field.setAccessible(isAccessible);
        return result;
    }

    private static Field getField(String fieldName, Class<?> clazz) throws NoSuchFieldException {
        Field field = getFromCache(fieldName, clazz);
        if (field == null) {
           field = getDirectly(fieldName, clazz);
        }
        return field;
    }

    private static Field getFromCache(String fieldName, Class<?> clazz) {
       SoftReference< Map<String, Field>> reference = cache.get(clazz);
        if (reference == null || reference.get() == null) {
            return null;
        } else {
            Map<String, Field> map = reference.get();
            if (map != null) {
                return map.get(fieldName);
            } else {
                return null;
            }
        }
    }

    private static Field getDirectly(String fieldName, Class<?> clazz) throws NoSuchFieldException {
        Field field = clazz.getDeclaredField(fieldName);
        putToCache(clazz, fieldName, field);
        return field;
    }

    private static void putToCache(Class<?> clazz, String fieldName, Field field) {
        SoftReference<Map<String, Field>> reference = cache.get(clazz);
        if (reference == null || reference.get() == null) {
            Map<String, Field> map = new HashMap<>();
            map.put(fieldName, field);
            cache.put(clazz, new SoftReference<>(map));
        } else {
            Map<String, Field> map = reference.get();
            if (map != null) {
                map.put(fieldName, field);
            }
        }
    }
}
