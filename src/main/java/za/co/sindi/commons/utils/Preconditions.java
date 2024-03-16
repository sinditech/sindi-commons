/**
 * 
 */
package za.co.sindi.commons.utils;

/**
 * @author Buhake Sindi
 * @since 02 August 2014
 *
 */
public final class Preconditions {

	private Preconditions() {
		throw new AssertionError("Private constructor.");
	}
	
	public static void checkArgument(boolean condition) {
		if (!condition) {
			throw new IllegalArgumentException();
		}
	}
	
	public static void checkArgument(boolean condition, String message) {
		if (!condition) {
			throw new IllegalArgumentException(message);
		}
	}
	
	public static void checkArgument(boolean condition, String message, Object... arguments) {
		if (!condition) {
			throw new IllegalArgumentException(String.format(message, arguments));
		}
	}
	
	public static void checkNotNull(Object object) {
		if (object == null) {
			throw new NullPointerException();
		}
	}
	
	public static void checkNotNull(Object object, String message) {
		if (object == null) {
			throw new NullPointerException(message);
		}
	}
	
	public static void checkNotNull(Object object, String message, Object... arguments) {
		if (object == null) {
			throw new NullPointerException(String.format(message, arguments));
		}
	}
	
	public static void checkState(boolean condition) {
		if (!condition) {
			throw new IllegalStateException();
		}
	}
	
	public static void checkSate(boolean condition, String message) {
		if (!condition) {
			throw new IllegalStateException(message);
		}
	}
	
	public static void checkState(boolean condition, String message, Object... arguments) {
		if (!condition) {
			throw new IllegalStateException(String.format(message, arguments));
		}
	}
}
