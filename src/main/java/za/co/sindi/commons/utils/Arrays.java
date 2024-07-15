/**
 * 
 */
package za.co.sindi.commons.utils;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Buhake Sindi
 * @since 05/08/2023
 */
public class Arrays {
	
	public static final int[] EMPTY_INT_PRIMITIVE_ARRAY = {};// new int[0];
	public static final long[] EMPTY_LONG_PRIMITIVE_ARRAY = {};//new long[0];
	public static final Integer[] EMPTY_INTEGER_OBJECT_ARRAY = {};//new Integer[0];
	public static final Long[] EMPTY_LONG_OBJECT_ARRAY = {};//new Long[0];
	public static final String[] EMPTY_STRING_ARRAY = {};
	
	private Arrays() {
		throw new AssertionError("Private constructor.");
	}
	
	@SafeVarargs
	public static <T> Set<T> asSet(T... t) {
		return new HashSet<>(java.util.List.of(t));
	}
	
	@SafeVarargs
	public static <T> T[] append(T[] array, T... values) {
		return concat(array, values);
	}
	
	@SafeVarargs
	public static <T> T[] concat(T[] first, T[]... arrays) {
		//Source copied from: http://stackoverflow.com/questions/80476/how-to-concatenate-two-arrays-in-java/784842#784842
		int totalLength = first.length;
		for (T[] array : arrays) {
			totalLength += array.length;
		}
		T[] result = java.util.Arrays.copyOf(first, totalLength);
		int offset = first.length;
		for (T[] array : arrays) {
			System.arraycopy(array, 0, result, offset, array.length);
			offset += array.length;
		}
		
		return result;
	}

	@SafeVarargs
	public static <T> T[] prepend(final T[] array, final T... values) {
		return concat(values, array);
	}
	
	@SafeVarargs
	public static <T> T[] toArray(T... args) {
		return args;
	}
	
	public static int[] toPrimitives(Integer[] array) {
		if (array == null) {
			return null;
		}
		
		if (array.length == 0) {
			return EMPTY_INT_PRIMITIVE_ARRAY;
		}
		
		int[] newArray = new int[array.length];
		for (int i = 0; i < array.length; i++) {
			newArray[i] = array[i].intValue();
		}
		
		return newArray;
	}
	
	public static int[] toPrimitives(Integer[] array, int defaultValueForNull) {
		if (array == null) {
			return null;
		}
		
		if (array.length == 0) {
			return EMPTY_INT_PRIMITIVE_ARRAY;
		}
		
		int[] newArray = new int[array.length];
		for (int i = 0; i < array.length; i++) {
			Integer value = array[i];
			newArray[i] = value == null ? defaultValueForNull : value.intValue();
		}
		
		return newArray;
	}
	
	public static long[] toPrimitives(Long[] array) {
		if (array == null) {
			return null;
		}
		
		if (array.length == 0) {
			return EMPTY_LONG_PRIMITIVE_ARRAY;
		}
		
		long[] newArray = new long[array.length];
		for (int i = 0; i < array.length; i++) {
			newArray[i] = array[i].longValue();
		}
		
		return newArray;
	}
	
	public static long[] toPrimitives(Long[] array, long defaultValueForNull) {
		if (array == null) {
			return null;
		}
		
		if (array.length == 0) {
			return EMPTY_LONG_PRIMITIVE_ARRAY;
		}
		
		long[] newArray = new long[array.length];
		for (int i = 0; i < array.length; i++) {
			Long value = array[i];
			newArray[i] = value == null ? defaultValueForNull : value.longValue();
		}
		
		return newArray;
	}
	
	public static long[] toPrimitiveArray(Long[] array, long defaultValueForNull) {
		if (array == null) {
			return null;
		}
		
		if (array.length == 0) {
			return EMPTY_LONG_PRIMITIVE_ARRAY;
		}
		
		long[] newArray = new long[array.length];
		for (int i = 0; i < array.length; i++) {
			Long value = array[i];
			newArray[i] = value == null ? defaultValueForNull : value.longValue();
		}
		
		return newArray;
	}
	
	public static Integer[] toObject(int[] array) {
		if (array == null) {
			return null;
		}
		
		if (array.length == 0) {
			return EMPTY_INTEGER_OBJECT_ARRAY;
		}
		
		Integer[] newArray = new Integer[array.length];
		for (int i = 0; i < array.length; i++) {
			newArray[i] = array[i];
		}
		
		return newArray;
	}
	
	public static Long[] toObject(long[] array) {
		if (array == null) {
			return null;
		}
		
		if (array.length == 0) {
			return EMPTY_LONG_OBJECT_ARRAY;
		}
		
		Long[] newArray = new Long[array.length];
		for (int i = 0; i < array.length; i++) {
			newArray[i] = array[i];
		}
		
		return newArray;
	}
}
