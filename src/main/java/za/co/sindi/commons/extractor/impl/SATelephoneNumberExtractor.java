/**
 * 
 */
package za.co.sindi.commons.extractor.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import za.co.sindi.commons.validator.impl.SATelephoneNumberValidator;

/**
 * @author Buhake Sindi
 * @since 30 June 2026
 */
public class SATelephoneNumberExtractor extends TelephoneNumberExtractor {
	
	private static final SATelephoneNumberValidator VALIDATOR = new SATelephoneNumberValidator();

	@Override
	public Result<TelephoneNumber> extract(String input) {
		// TODO Auto-generated method stub
		Result<TelephoneNumber> extractedResults = super.extract(input);
		if (!extractedResults.passed()) return extractedResults;
		
		List<TelephoneNumber> results = new ArrayList<>();
		for (TelephoneNumber number : extractedResults.values()) {
			if (VALIDATOR.validate(number.normalisedValue()).isValid()) results.add(number);
		}
		
		return new Result<>(Set.copyOf(results));
	} 
}
