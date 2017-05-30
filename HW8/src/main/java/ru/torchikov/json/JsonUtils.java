package ru.torchikov.json;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by Torchikov Sergei on 29.05.2017.
 * Creates json string from Java object
 */
public final class JsonUtils {
	private static final Set<Class<?>> SIMPLE_TYPES = new HashSet<>();
	static {
		SIMPLE_TYPES.addAll(Arrays.asList(boolean.class, Boolean.class, byte.class, Byte.class, short.class, Short.class, int.class, Integer.class,
				long.class, Long.class, float.class, Float.class, double.class, Double.class, String.class));
	}

	private JsonUtils() {
	}

	public static String toJson(Object object) throws IllegalAccessException {
		return toJSONObject(object).toJSONString();
	}

	private static JSONObject toJSONObject(Object object) throws IllegalAccessException {
		Map<String, Object> map = new HashMap<>();
		Class<?> clazz = object.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			boolean accessible = field.isAccessible();
			field.setAccessible(true);
			Object fieldValue = field.get(object);
			String fieldName = field.getName();
			if (Objects.isNull(fieldValue)) {
				map.put(fieldName, null);
			}else if (isPrimitive(fieldValue)) {
				map.put(fieldName, fieldValue);
			}else if (isArray(field)) {
				JSONArray array = createArray(fieldValue);
				map.put(fieldName, array);
			} else if (isCollection(fieldValue)) {
				map.put(fieldName, fieldValue);
			} else {
				map.put(fieldName, toJSONObject(fieldValue));
			}
			field.setAccessible(accessible);
		}
		return new JSONObject(map);
	}

	@SuppressWarnings("unchecked")
	private static JSONArray createArray(Object array) throws IllegalAccessException {
		JSONArray jsonArray = new JSONArray();
		int length = Array.getLength(array);
		for (int i = 0; i < length; i++) {
			Object arrayElement = Array.get(array, i);
			if (arrayElement.getClass().isArray()) {
				jsonArray.add(createArray(arrayElement));
			}else {
				jsonArray.add(arrayElement);
			}
		}
		return jsonArray;
	}

	private static boolean isArray(Field field) {
		return field.getType().isArray();
	}

	private static boolean isPrimitive(Object object) {
		return SIMPLE_TYPES.contains(object.getClass());
	}

	private static boolean isCollection(Object object) {
		return object instanceof Collection || object instanceof Map;
	}
}
