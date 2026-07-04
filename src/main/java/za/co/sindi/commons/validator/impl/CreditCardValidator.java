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
public class CreditCardValidator implements Validator<String> {
	
	private static final Logger LOGGER = Logger.getLogger(CreditCardValidator.class.getName());
	
	private static final String CARD_REGEXP = 
	        "^(?:4[0-9]{12}(?:[0-9]{3})?|" +           // Visa
	        "5[1-5][0-9]{14}|" +                       // Mastercard (Legacy)
	        "2(22[1-9]|2[3-9][0-9]|[3-6][0-9]{2}|7[0-1][0-9]|720)[0-9]{12}|" + // Mastercard (2017 bins)
	        "3[47][0-9]{13}|" +                        // AMEX
	        "3(?:0[0-5]|[68][0-9])[0-9]{11}|" +        // Diners Club
	        "6(?:011|5[0-9]{2})[0-9]{12}|" +           // Discover
	        "(?:2131|1800|35[0-9]{3})[0-9]{11})$";     // JCB

	private static final Pattern CARD_REGEXP_PATTERN = Pattern.compile(CARD_REGEXP);

	@Override
	public ValidationResult<String> validate(String value) {
		// TODO Auto-generated method stub
		if (LOGGER.isLoggable(Level.INFO)) {
			LOGGER.log(Level.INFO, String.format("Validating potential value of a card number '%s'.", value));
		}
		
		if (Strings.isNullOrEmpty(value) || 
			value.length() < 13 || value.length() > 19 ||
			!CARD_REGEXP_PATTERN.matcher(value).matches()
		) {
			LOGGER.log(Level.SEVERE, "Invalid value provided (cannot be a valid card number).");
			return ValidationResult.errors(value, "Invalid value provided (cannot be a valid card number).");
		}
		
		int sum = 0;
        boolean shouldDouble = false;
        
        // Loop backwards from the rightmost digit
        for (int i = value.length() - 1; i >= 0; i--) {
            int digit = value.charAt(i) - '0';

            if (shouldDouble) {
                digit *= 2;
                // If doubling results in a two-digit number, add its digits (e.g., 14 -> 1+4 = 5)
                // This is mathematically equivalent to subtracting 9
                if (digit > 9) {
                    digit -= 9;
                }
            }

            sum += digit;
            shouldDouble = !shouldDouble; // Alternate for every second digit
        }

        // The card is valid if the total sum is a multiple of 10
        if (sum % 10 != 0) {
        	LOGGER.log(Level.SEVERE, "Invalid card number.");
			return ValidationResult.errors(value, "Invalid card number.");
        }
        
        return ValidationResult.valid(value);
	}
}
