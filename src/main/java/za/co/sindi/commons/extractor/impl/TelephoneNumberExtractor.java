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
import za.co.sindi.commons.extractor.impl.TelephoneNumberExtractor.TelephoneNumber;

/**
 * @author Buhake Sindi
 * @since 30 June 2026
 */
public class TelephoneNumberExtractor implements Extractor<String, TelephoneNumber> {
	
	protected final Logger LOGGER = Logger.getLogger(this.getClass().getName());
	
	private static final String TELEPHONE_NUMBER_EXTRACTOR_REGEXP = "(?:(?:\\+\\d{1,4}[-.\\s]?)?(?:\\(\\d{1,4}\\)|\\d{1,4})[-.\\s]?)?\\d{1,4}[-.\\s]?\\d{1,4}(?:[-.\\s]?\\d{1,9})?"; //"(\\+?\\d{1,4}[\\s.-]?\\(?\\d{1,3}?\\)?([\\s.-]?\\d{1,4}){2,3})"; // "\\b(?:\\d{3}[-.]?\\d{3}[-.]?\\d{4}|\\d{10})\\b"
	private static final Pattern TELEPHONE_NUMBER_EXTRACTOR_REGEXP_PATTERN = Pattern.compile(TELEPHONE_NUMBER_EXTRACTOR_REGEXP);
	
	@Override
	public Result<TelephoneNumber> extract(String input) {
		// TODO Auto-generated method stub
		List<TelephoneNumber> results = new ArrayList<>();
		Matcher matcher = TELEPHONE_NUMBER_EXTRACTOR_REGEXP_PATTERN.matcher(input);

        while (matcher.find()) {
            String candidate = matcher.group();
            String normalisedCandidate = normalise(candidate);
            System.out.println("Candidate: " + candidate);
            results.add(new TelephoneNumber(candidate, normalisedCandidate));
        }
        
        return new Result<>(Set.copyOf(results));
	}
	
	private String normalise(final String text) {
		if (text == null) {
            return "";
        }

        return text
                .replaceAll("\\(0\\)", " ")
                .replaceAll("\\s+", "")
                .replaceAll("-", "")
                .replaceAll("\\(|\\)", "")
                .trim();
	}
	
	public static record TelephoneNumber(String extractedValue, String normalisedValue) {}

//	public static void main(String[] args) {
//		String paragraph = "Contact our support team at +1 555 123 4567 or our UK " +
//                "office at +44-20-7946-0958. You can also try reaching "+
//                "us locally at 011-555-0199 (not an international number)." +
//                "Call us at +1 (555) 019-9234 or +44 20 7946 0958. " +
//                "Local numbers like (011) 555-1234 or +27(0)825551234 work too.";
//		
//		SATelephoneNumberExtractor extractor = new SATelephoneNumberExtractor();
//		List<Result<String>> results = extractor.extract(paragraph);
//		for(Result<String> result : results) {
//			System.out.println("Telephone number found: " + result.value());
//		}
//	}
}
