/**
 * 
 */
package za.co.sindi.commons.utils;

import java.util.Locale;

/**
 * @author Buhake Sindi
 * @since 12 May 2015
 *
 */
public final class Locales {

	private Locales() {
		throw new AssertionError("Private constructor.");
	}
	
	/**
	 * 
	 * @param language The typical language value is a two or three-letter language code as defined in ISO639.
	 * @return
	 */
	public static Locale newLocale(String language) {
		return newLocale(language, null);
	}
	
	/**
	 * 
	 * @param language The typical language value is a two or three-letter language code as defined in ISO639.
	 * @param region The typical region value is a two-letter ISO 3166 code or a three-digit UN M.49 area code. 
	 * @return
	 */
	public static Locale newLocale(String language, String region) {
		return new Locale.Builder().setLanguage(language).setRegion(region).build();
	}
}
