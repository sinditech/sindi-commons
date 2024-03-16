/**
 * 
 */
package za.co.sindi.commons.validator;

/**
 * @author Buhake Sindi
 * @since 23 September 2023
 */
public interface Validator<T> {

	public boolean isValid(final T value);
}
