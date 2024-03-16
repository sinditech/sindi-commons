/**
 * 
 */
package za.co.sindi.commons.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Buhake Sindi
 * @since 03 December 2015
 *
 */
public class Primitives {

	private static final Map<Class<?>, Class<?>> PRIMITIVE_TO_WRAPPER_TYPES;
	private static final Map<Class<?>, Class<?>> WRAPPER_TO_PRIMITIVE_TYPES;
	
	static {
		Map<Class<?>, Class<?>> primitiveToWrapperTypes = new HashMap<>();
		Map<Class<?>, Class<?>> wrapperToPrimitiveTypes = new HashMap<>();
		
		put(primitiveToWrapperTypes, wrapperToPrimitiveTypes, boolean.class, Boolean.class);
		put(primitiveToWrapperTypes, wrapperToPrimitiveTypes, byte.class, Byte.class);
		put(primitiveToWrapperTypes, wrapperToPrimitiveTypes, char.class, Character.class);
		put(primitiveToWrapperTypes, wrapperToPrimitiveTypes, double.class, Double.class);
		put(primitiveToWrapperTypes, wrapperToPrimitiveTypes, float.class, Float.class);
		put(primitiveToWrapperTypes, wrapperToPrimitiveTypes, int.class, Integer.class);
		put(primitiveToWrapperTypes, wrapperToPrimitiveTypes, long.class, Long.class);
		put(primitiveToWrapperTypes, wrapperToPrimitiveTypes, short.class, Short.class);
		put(primitiveToWrapperTypes, wrapperToPrimitiveTypes, void.class, Void.class);
		
		PRIMITIVE_TO_WRAPPER_TYPES = Collections.unmodifiableMap(primitiveToWrapperTypes);
		WRAPPER_TO_PRIMITIVE_TYPES = Collections.unmodifiableMap(wrapperToPrimitiveTypes);
	}
	
	private Primitives() {
		throw new AssertionError("Private Constructor.");
	}
	
	private static void put(Map<Class<?>, Class<?>> forward, Map<Class<?>, Class<?>> backward, Class<?> key, Class<?> value) {
		forward.put(key, value);
		backward.put(value, key);
	}
	
	public static boolean isPrimitive(Class<?> type) {
		Preconditions.checkArgument(type != null, "No Class type was specified.");
		return PRIMITIVE_TO_WRAPPER_TYPES.containsKey(type);
	}
	
	public static boolean isWrapperType(Class<?> type) {
		Preconditions.checkArgument(type != null, "No Class type was specified.");
		return WRAPPER_TO_PRIMITIVE_TYPES.containsKey(type);
	}
	
	public static Class<?> wrap(Class<?> type) {
		if (isPrimitive(type)) {
			return PRIMITIVE_TO_WRAPPER_TYPES.get(type);
		}
		
		return null;
	}
	
	public static Class<?> unwrap(Class<?> type) {
		if (isWrapperType(type)) {
			return WRAPPER_TO_PRIMITIVE_TYPES.get(type);
		}
		
		return null;
	}
}
