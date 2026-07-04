/**
 * 
 */
package za.co.sindi.commons.extractor.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import za.co.sindi.commons.extractor.Extractor;
import za.co.sindi.commons.validator.impl.SAIDNumberValidator;

/**
 * @author Buhake Sindi
 * @since 30 June 2026
 */
public class SAIDNumberExtractor implements Extractor<String, String> {
	
	private static final Logger LOGGER = Logger.getLogger(SAIDNumberExtractor.class.getName());
	
	private static final SAIDNumberValidator VALIDATOR = new SAIDNumberValidator();
	private static final String ID_REGEX_STRING = "\\b\\d{13}\\b";
	private static final Pattern ID_REGEX_PATTERN = Pattern.compile(ID_REGEX_STRING);

	@Override
	public Result<String> extract(String input) {
		// TODO Auto-generated method stub
		List<String> results = new ArrayList<>();
		Matcher matcher = ID_REGEX_PATTERN.matcher(input);

        while (matcher.find()) {
            String candidate = matcher.group();
            if (VALIDATOR.validate(candidate).isValid()) {
                results.add(candidate);
            }
        }
        
        return new Result<>(Set.copyOf(results));
	}
	
//	public static void main(String[] args) {
//		String paragraph = "Please contact our support team at 0800123456. "
//                + "Our client John Doe with ID 8309085153085 is registered "
//                + "and his secondary ID is 7901010000080.";
//		
//		SAIDNumberExtractor extractor = new SAIDNumberExtractor();
//		List<Result<String>> results = extractor.extract(paragraph);
//		for(Result<String> result : results) {
//			System.out.println("ID Number found: " + result.value());
//		}
//	}
}
