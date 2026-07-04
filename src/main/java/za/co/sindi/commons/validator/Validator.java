/**
 * 
 */
package za.co.sindi.commons.validator;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Buhake Sindi
 * @since 23 September 2023
 */
public interface Validator<T> {

	public ValidationResult<T> validate(final T value);
	
	public static record ValidationError(String message) {
		
		public ValidationError {
			message = Objects.requireNonNull(message, "A validation error message is required.");
		}
	}
	
	public static record ValidationResult<T>(T inputValue, Set<ValidationError> errors) {
		
		public ValidationResult(T inputValue) {
			this(inputValue, Set.of());
		}
		
		public boolean isValid() {
			return errors.isEmpty();
		}
		
		public static <T> ValidationResult<T> valid(T inputValue) {
			return new ValidationResult<T>(inputValue);
		}
		
		public static <T> ValidationResult<T> errors(T inputValue, String... messages) {
			return new ValidationResult<T>(inputValue, Arrays.stream(messages).map(message -> new ValidationError(message)).collect(Collectors.toSet()));
		}
	}
}
