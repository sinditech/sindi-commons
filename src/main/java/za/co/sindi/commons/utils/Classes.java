/**
 * 
 */
package za.co.sindi.commons.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Buhake Sindi
 * @since 28 July 2015
 *
 */
public final class Classes {

	private static final Map<Class<?>, Class<?>> PRIMITIVE_TO_WRAPPER_MAP = new HashMap<>();
	private static final Map<Class<?>, Class<?>> WRAPPER_TO_PRIMITIVE_MAP = new HashMap<>();
	
	static {
		WRAPPER_TO_PRIMITIVE_MAP.put(Boolean.class, boolean.class);
		WRAPPER_TO_PRIMITIVE_MAP.put(Byte.class, byte.class);
		WRAPPER_TO_PRIMITIVE_MAP.put(Character.class, char.class);
		WRAPPER_TO_PRIMITIVE_MAP.put(Double.class, double.class);
		WRAPPER_TO_PRIMITIVE_MAP.put(Float.class, float.class);
		WRAPPER_TO_PRIMITIVE_MAP.put(Integer.class, int.class);
		WRAPPER_TO_PRIMITIVE_MAP.put(Long.class, long.class);
		WRAPPER_TO_PRIMITIVE_MAP.put(Short.class, short.class);
		
		PRIMITIVE_TO_WRAPPER_MAP.put(boolean.class, Boolean.class);
		PRIMITIVE_TO_WRAPPER_MAP.put(byte.class, Byte.class);
		PRIMITIVE_TO_WRAPPER_MAP.put(char.class, Character.class);
		PRIMITIVE_TO_WRAPPER_MAP.put(double.class, Double.class);
		PRIMITIVE_TO_WRAPPER_MAP.put(float.class, Float.class);
		PRIMITIVE_TO_WRAPPER_MAP.put(int.class, Integer.class);
		PRIMITIVE_TO_WRAPPER_MAP.put(long.class, Long.class);
		PRIMITIVE_TO_WRAPPER_MAP.put(short.class, Short.class);
	}
	
	private Classes() {
		throw new AssertionError("Private Constructor.");
	}
	
	public static ClassLoader getClassLoader() {
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		
		if (cl == null) {
			cl = Classes.class.getClassLoader();
		}
		
		if (cl == null) {
			cl = ClassLoader.getSystemClassLoader();
		}
		
		return cl;
	}
	
	public static Class<?> getPrimitiveTypeFor(Class<?> wrapperClass) {
		return WRAPPER_TO_PRIMITIVE_MAP.get(wrapperClass);
	}
	
	public static Class<?> getWrapperTypeFor(Class<?> primitiveClass) {
		Preconditions.checkArgument(primitiveClass.isPrimitive(), "Class " + primitiveClass.getName() + " is not a primitive type.");
		return WRAPPER_TO_PRIMITIVE_MAP.get(primitiveClass);
	}
	
	public static Class<?> getClass(final String className) throws ClassNotFoundException {
		return getClass(className, true);
	}
	
	public static Class<?> getClass(final String className, boolean initialize) throws ClassNotFoundException {
		return getClass(getClassLoader(), className, initialize);
	}
	
	public static Class<?> getClass(ClassLoader classLoader, final String className) throws ClassNotFoundException {
		return getClass(classLoader, className, true);
	}
	
	public static Class<?> getClass(ClassLoader classLoader, final String className, boolean initialize) throws ClassNotFoundException {
		return Class.forName(className, initialize, classLoader);
	}
}
