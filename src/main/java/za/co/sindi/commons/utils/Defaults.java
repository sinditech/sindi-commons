/**
 * 
 */
package za.co.sindi.commons.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Buhake Sindi
 * @since 28 July 2015
 *
 */
public final class Defaults {

	private static final Map<Class<?>, Object> DEFAULTS; // = new HashMap<>();
	
	static {
//		DEFAULTS.put(boolean.class, Boolean.FALSE);
//		DEFAULTS.put(byte.class, new Byte((byte)0));
//		DEFAULTS.put(short.class, new Short((short)0));
//		DEFAULTS.put(int.class, new Integer(0));
//		DEFAULTS.put(long.class, new Long(0L));
//		DEFAULTS.put(char.class, new Character('\0'));
//		DEFAULTS.put(float.class, new Float(0.0F));
//		DEFAULTS.put(double.class, new Double(0.0));
		Map<Class<?>, Object> map = new HashMap<Class<?>, Object>();
	    put(map, boolean.class, false);
	    put(map, char.class, '\0');
	    put(map, byte.class, (byte) 0);
	    put(map, short.class, (short) 0);
	    put(map, int.class, 0);
	    put(map, long.class, 0L);
	    put(map, float.class, 0f);
	    put(map, double.class, 0d);
	    DEFAULTS = Collections.unmodifiableMap(map);
	}
	
	/**
	 * 
	 */
	private Defaults() {
		super();
		// TODO Auto-generated constructor stub
		throw new AssertionError("Private Constructor.");
	}
	
	private static <T> void put(Map<Class<?>, Object> map, Class<T> type, T value) {
		map.put(type, value);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getDefaultValue(Class<T> clazz) {
		Preconditions.checkArgument(clazz != null && clazz.isPrimitive(), "Class type is null or is not of a primitive type.");
		return (T) DEFAULTS.get(clazz);
	}
}
