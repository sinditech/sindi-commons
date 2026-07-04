/**
 * 
 */
package za.co.sindi.commons.validator.impl;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import za.co.sindi.commons.utils.Strings;
import za.co.sindi.commons.validator.Validator;

/**
 * @author Buhake Sindi
 * @since 04 September 2023
 */
public class EmailAddressValidator implements Validator<String> {
	
	private static final Logger LOGGER = Logger.getLogger(EmailAddressValidator.class.getName());
	
	private static final String EMAIL_ADDRESS_REGEX_STRING = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
	private static final Pattern EMAIL_ADDRESS_REGEX_PATTERN = Pattern.compile(EMAIL_ADDRESS_REGEX_STRING);

	@Override
	public ValidationResult<String> validate(String value) {
		// TODO Auto-generated method stub
		if (LOGGER.isLoggable(Level.INFO)) {
			LOGGER.log(Level.INFO, String.format("Validating potential value of an email address '%s'.", value));
		}
		
		if (Strings.isNullOrEmpty(value) || 
			!EMAIL_ADDRESS_REGEX_PATTERN.matcher(value).matches()
		) {
			LOGGER.log(Level.SEVERE, "Invalid email address.");
			return ValidationResult.errors(value, "Invalid email address.");
		}
		
		return ValidationResult.valid(value);
	}
	
	public static void main(String[] args) {
		String email = "tester+sindi@hotmail.";
		EmailAddressValidator validator = new EmailAddressValidator();
		ValidationResult<String> result = validator.validate(email);
		System.out.println(result.isValid());
	}
}
