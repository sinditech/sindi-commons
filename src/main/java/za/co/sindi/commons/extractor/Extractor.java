/**
 * 
 */
package za.co.sindi.commons.extractor;

import java.util.Set;

/**
 * @author Buhake Sindi
 * @since 30 June 2026
 */
public interface Extractor<I, R> {

	public Result<R> extract(final I input);
	
	public static record Result<R>(Set<R> values) {
		
		public boolean passed() {
			return !values.isEmpty();
		}
	}
}
