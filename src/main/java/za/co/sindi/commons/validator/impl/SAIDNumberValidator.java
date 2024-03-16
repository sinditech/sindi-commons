/**
 * 
 */
package za.co.sindi.commons.validator.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import za.co.sindi.commons.utils.Strings;
import za.co.sindi.commons.validator.Validator;

/**
 * @author Buhake Sindi
 * @since 04 September 2023
 */
public class SAIDNumberValidator implements Validator<String> {
	
	private static final Logger LOGGER = Logger.getLogger(SAIDNumberValidator.class.getName());
	
	private static final String ID_REGEX_STRING = "(\\d{2})(((0[13578]|1[02])(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)(0[1-9]|[12][0-9]|30))|((02)(0[1-9]|1[0-9]|2[0-8]))|((02)29))[0-9]{4}[0-1]{1}[8-9]{1}[0-9]{1}"; // was "[0-9]{10}[0-1]{1}[8-9]{1}[0-9]{1}"
	private static final Pattern ID_REGEX_PATTERN = Pattern.compile(ID_REGEX_STRING);

	@Override
	public boolean isValid(String value) {
		// TODO Auto-generated method stub
		if (LOGGER.isLoggable(Level.INFO)) {
			LOGGER.log(Level.INFO, String.format("Validating potential value of an SA ID Number '%s'.", value));
		}
		
		if (Strings.isNullOrEmpty(value) || 
			value.length() != 13 || 
			!ID_REGEX_PATTERN.matcher(value).matches() || 
			!isValidDate(value) //Check if the date is valid or not...
		) {
			LOGGER.log(Level.SEVERE, "Invalid value provided (cannot be a SA ID Number)");
			return false;
		}
		
		//We haven't returned, so the checks and balances are good, let's begin validating checksum....
		int definedChecksum = Character.getNumericValue(value.charAt(12));
		int oddTotal = 0;
		String evenValues = "";
		for (int i = 0; i < value.length() - 1; i++) {
			char c = value.charAt(i);
			if (i % 2 == 0) {
				oddTotal += Character.getNumericValue(c);
			} else {
				evenValues += String.valueOf(c);
			}
		}
		
		evenValues = String.valueOf(Integer.parseInt(evenValues) * 2);
		int evenTotal = 0;
		for (int i = 0; i < evenValues.length(); i++) {
			evenTotal += Character.getNumericValue(evenValues.charAt(i));
		}
		
		int sum = oddTotal + evenTotal;
		int calculatedChecksum = (10 - (sum % 10)) % 10;
		if (definedChecksum != calculatedChecksum) {
			LOGGER.log(Level.SEVERE, "Invalid checksum.");
			return false;
		}
		
		//Add info messages here.
		if (LOGGER.isLoggable(Level.INFO)) {
			int sex = Character.getNumericValue(value.charAt(6));
			LOGGER.log(Level.INFO, (sex >= 0 && sex <= 4) ? "Sex is female" : "Sex is male");
			
			int citizenship = Character.getNumericValue(value.charAt(10));
			LOGGER.log(Level.INFO, (citizenship == 0) ? "Person is a SA Citizen" : "Person is a permanent citizen");
		}
		
		return true;
	}

	private boolean isValidDate(final String dateString) {
		Calendar calendar = Calendar.getInstance();
		calendar.setLenient(false);
		int currentYear = calendar.get(Calendar.YEAR);
		int currentCentury = currentYear / 100;
		int year = Integer.parseInt(String.valueOf(currentCentury) + dateString.substring(0, 2));
		if ((year + 16) > currentYear) {
			currentCentury--;
		}
		
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		df.setLenient(false);
		synchronized (df) {
			try {
				df.parse(String.valueOf(currentCentury) + dateString.substring(0, 6));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				return false;
			}
		}
		
		return true;
	}
}
