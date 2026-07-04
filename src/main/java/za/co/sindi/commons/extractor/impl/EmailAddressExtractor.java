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
import za.co.sindi.commons.validator.impl.EmailAddressValidator;

/**
 * @author Buhake Sindi
 * @since 01 July 2026
 */
public class EmailAddressExtractor implements Extractor<String, String> {
	
	private static final Logger LOGGER = Logger.getLogger(EmailAddressExtractor.class.getName());
	
	private static final EmailAddressValidator VALIDATOR = new EmailAddressValidator();
	private static final String EMAIL_ADDRESS_REGEX_STRING = "[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}";
	private static final Pattern EMAIL_ADDRESS_REGEX_PATTERN = Pattern.compile(EMAIL_ADDRESS_REGEX_STRING);

	@Override
	public Result<String> extract(String input) {
		// TODO Auto-generated method stub
		List<String> results = new ArrayList<>();
		Matcher matcher = EMAIL_ADDRESS_REGEX_PATTERN.matcher(input);

        while (matcher.find()) {
            String candidate = matcher.group();
            if (VALIDATOR.validate(candidate).isValid()) {
                results.add(candidate);
            }
        }
        
		return new Result<>(Set.copyOf(results));
	}
	
//	public static void main(String[] args) {
//		String paragraph = "Please contact our support team at support@example.com or " +
//                "sales@sales.org for more information. You can also reach out " +
//                "to hr.dept@company.co.uk if you have any questions.";
//		
//		EmailAddressExtractor extractor = new EmailAddressExtractor();
//		List<Result<String>> results = extractor.extract(paragraph);
//		for(Result<String> result : results) {
//			System.out.println("Email address found: " + result.value());
//		}
//	}
}
