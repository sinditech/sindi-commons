/**
 * 
 */
package za.co.sindi.commons.utils;

/**
 * @author Buhake Sindi
 * @since 15 September 2023
 */
public final class Objects {

	private Objects() {
		throw new AssertionError("Private constructor.");
	}
	
	@SafeVarargs
	public static <T> T coalesce(T... values) {
		if (values != null) {
			for (T value : values) {
				if (value != null) {
					return value;
				}
			}
		}
		
		return null;
	}
	
	public static boolean areAllNulls(Object... objects) {
		if (objects != null) {
			for (Object object : objects) {
				if (object != null) return false;
			}
		}
		
		return true;
	}
}
