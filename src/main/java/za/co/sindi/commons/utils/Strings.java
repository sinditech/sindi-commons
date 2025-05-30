/**
 * 
 */
package za.co.sindi.commons.utils;

import java.util.Locale;
import java.util.Objects;

/**
 * @author Buhake Sindi
 * @since 21 March 2014
 *
 */
public final class Strings {

	public static final String EMPTY = "";
	
	private Strings() {
		throw new AssertionError("Private Constructor.");
	}
	
	/**
	 * Checks if the {@link CharSequence} is null, empty or blank.
	 * 
	 * @param str
	 * @return true if the criteria fits, false otherwise.
	 */
	public static boolean isBlank(CharSequence str) {
		if (str != null) {
			for (int i = 0; i < str.length(); i++) {
				if (!Character.isWhitespace(str.charAt(i))) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	/**
	 * Checks if the {@link CharSequence} is null.
	 * 
	 * @param str the {@link CharSequence} (such as {@link String}).
	 * @return <code>if (s == null)</code>
	 */
	public static boolean isNull(CharSequence str) {
		return Objects.isNull(str);
	}
	
//	/**
//	 * Checks if the {@link CharSequence} is empty.
//	 * 
//	 * @param str the {@link CharSequence} (such as {@link String}).
//	 * @return <code>if (s != null && s.length() == 0)</code>
//	 */
//	public static boolean isEmpty(CharSequence str) {
//		return !isNull(str) && str.length() == 0;
//	}
	
	/**
	 * Checks if the {@link CharSequence} is null or empty.
	 * 
	 * @param str the {@link CharSequence} (such as {@link String}).
	 * @return <code>if (s == null || s.length() == 0)</code>
	 */
	public static boolean isNullOrEmpty(CharSequence str) {
		return isNull(str) || str.length() == 0/*isEmpty(str)*/;
	}
	
	/**
	 * Capitalize the given string by uppercasing the first character. The rest of the characters are left unchanged.
	 * 
	 * @param str
	 * @return
	 */
	public static String capitalize(CharSequence str) {
		if (isNull(str)) {
			return null;
		}
		
		if (str.length() == 0) {
			return str.toString();
		}
		
		return new StringBuilder().append(Character.toTitleCase(str.charAt(0))).append(str.subSequence(1, str.length())).toString();
	}
	
	/**
	 * Uncapitalize the given string by lowercasing the first character. The rest of the characters are left unchanged.
	 * 
	 * @param str
	 * @return
	 */
	public static String uncapitalize(CharSequence str) {
		if (isNull(str)) {
			return null;
		}
		
		if (str.length() == 0) {
			return str.toString();
		}
		
		return new StringBuilder().append(Character.toLowerCase(str.charAt(0))).append(str.subSequence(1, str.length())).toString();
	}
	
	/**
	 * Check whether all the characters in this sequence/string are only in lowercases.
	 * 
	 * @param sequence
	 * @return
	 */
	public static boolean isAllLowerCase(CharSequence sequence) {
		if (isNullOrEmpty(sequence)) {
			return false;
		}
		
		for (int i = 0; i < sequence.length(); i++) {
			if (!Character.isLowerCase(sequence.charAt(i))) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Check whether all the characters in this sequence/string are only in uppercases.
	 * 
	 * @param sequence
	 * @return
	 */
	public static boolean isAllUpperCase(CharSequence sequence) {
		if (isNullOrEmpty(sequence)) {
			return false;
		}
		
		for (int i = 0; i < sequence.length(); i++) {
			if (!Character.isUpperCase(sequence.charAt(i))) {
				return false;
			}
		}
		
		return true;
	}
	
	public static String padLeft(CharSequence sequence, int length, char padCharacter) {
		if (isNull(sequence)) {
			return null;
		}
		
		StringBuilder sb = new StringBuilder();
		int paddingLength = length - sequence.length();
		if (paddingLength > 0) {
			for (int i = 0; i < paddingLength; i++) {
				sb.append(padCharacter);
			}
		}
		sb.append(sequence);
		
		return sb.toString();
	}
	
	public static String padRight(CharSequence sequence, int length, char padCharacter) {
		if (isNull(sequence)) {
			return null;
		}
		
		StringBuilder sb = new StringBuilder(sequence);
		int paddingLength = length - sequence.length();
		if (paddingLength > 0) {
			for (int i = 0; i < paddingLength; i++) {
				sb.append(padCharacter);
			}
		}
		
		return sb.toString();
	}
	
	public static String toLowerCase(String string) {
		return toLowerCase(string, Locale.ROOT);
	}
	
	public static String toLowerCase(String string, Locale locale) {
		if (isNull(string)) {
			return null;
		}
		
		return string.toLowerCase(locale == null ? Locale.ROOT : locale);
	}
	
	public static String toLowerCase(CharSequence sequence) {
		if (isNull(sequence)) {
			return null;
		}
		
		if (sequence instanceof String) {
			return toLowerCase((String)sequence);
		}
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < sequence.length(); i++) {
			char ch = sequence.charAt(i);
			if (Character.isLetter(ch) && !Character.isLowerCase(ch)) {
				sb.append(Character.toLowerCase(ch));
			} else {
				sb.append(ch);
			}
		}
		
		return sb.toString();
	}
	
	public static String toUpperCase(String string) {
		return toUpperCase(string, Locale.ROOT);
	}
	
	public static String toUpperCase(String string, Locale locale) {
		if (isNull(string)) {
			return null;
		}
		
		return string.toUpperCase(locale == null ? Locale.ROOT : locale);
	}
	
	public static String toUpperCase(CharSequence sequence) {
		if (isNull(sequence)) {
			return null;
		}
		
		if (sequence instanceof String) {
			return toUpperCase((String)sequence);
		}
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < sequence.length(); i++) {
			char ch = sequence.charAt(i);
			if (Character.isLetter(ch) && !Character.isUpperCase(ch)) {
				sb.append(Character.toUpperCase(ch));
			} else {
				sb.append(ch);
			}
		}
		
		return sb.toString();
	}
	
	public static String toWikiCase(CharSequence sequence) {
		return capitalize(toLowerCase(sequence));
	}
	
	public static String abbreviateRight(CharSequence sequence, int maxLength) {
		if (isNull(sequence)) {
			return null;
		}
		
		int length = sequence.length();
		if (length == 0) {
			return EMPTY;
		}
		
		if (length <= maxLength) {
			return sequence.toString();
		}
		
		return sequence.subSequence(0, maxLength - 3).toString() + "...";
	}
	
	public static String repeat(CharSequence sequence, int count) {
		Preconditions.checkArgument(count > 0, "Count can never be less than 1.");
		
		if (sequence == null) {
			return null;
		}
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < count; i++) {
			sb.append(sequence);
		}
		
		return sb.toString();
	}
	
	public static String left(CharSequence sequence, int length) {
		if (isNull(sequence)) {
			return null;
		}
		
		if (length < 0) {
			return EMPTY;
		}
		
		int sequenceLength = sequence.length();
		if (sequenceLength <= length) {
			return sequence.toString();
		}
		
		return sequence.subSequence(0, length).toString();
	}
	
	public static String right(CharSequence sequence, int length) {
		if (isNull(sequence)) {
			return null;
		}
		
		if (length < 0) {
			return EMPTY;
		}
		
		int sequenceLength = sequence.length();
		if (sequenceLength <= length) {
			return sequence.toString();
		}
		
		return sequence.subSequence(sequenceLength - length, sequenceLength).toString();
	}
	
	public static String requireNonEmpty(CharSequence sequence) {
		if (isNullOrEmpty(sequence)) {
			throw new IllegalArgumentException();
		}
		
		return sequence.toString();
	}
	
	public static String requireNonEmpty(CharSequence sequence, String message) {
		if (isNullOrEmpty(sequence)) {
			throw new IllegalArgumentException(message);
		}
		
		return sequence.toString();
	}
	
	/**
	 * Returns a new String composed of copies of the
     * {@code Object elements} joined together with a copy of
     * the specified {@code delimiter}.
     * 
	 * @param delimiter the delimiter that separates each element
	 * @param elements the elements to join together.
	 * @return a new {@code String} that is composed of the {@code elements}
     *         separated by the {@code delimiter}
	 */
	public static String join(final CharSequence delimiter, final Object... elements) {
        var elems = new String[elements.length];
        for (int i = 0; i < elements.length; i++) {
            elems[i] = String.valueOf(elements[i]);
        }
        return String.join(delimiter, elems);
	}
	
	/**
	 * Removes a substring only if it is at the end of a source string, otherwise returns the source string.
	 * A null source string will return null. An empty ("") source string will return the empty string. A null search string will return the source string.
	 * 
	 * @param sequence
	 * @param remove
	 * @return
	 */
	public static String removeEnd(final CharSequence sequence, final String remove) {
		if (!isNullOrEmpty(sequence) && sequence instanceof String str) {
			int index = str.lastIndexOf(remove);
			if (index > 0) return str.substring(0, index);
		}
		
		return sequence.toString();
	}
}
