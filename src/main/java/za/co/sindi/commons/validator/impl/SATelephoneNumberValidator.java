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
 * @see <a href="https://en.wikipedia.org/wiki/Telephone_numbers_in_South_Africa">Telephone numbers in South Africa</a>.
 */
public class SATelephoneNumberValidator implements Validator<String> {
	
	private static final Logger LOGGER = Logger.getLogger(SATelephoneNumberValidator.class.getName());
	
	private static final String SA_TELEPHONE_NUMBER_REGEXP = "^(\\+27|27|27|0)"
			+ "(" //Begin
			//01
			+ "1[0-8]" //Gauteng, Mpumalanga, Limpopo and North West
			
			//02
			+ "|2[1-4]|02[7-8]" //Western Cape and Northern Cape
			
			//03
			+ "|3[1-6]|039" //Kwazulu-Natal
			
			//04
			+ "|4[0-9]" //Eastern Cape and eastern parts of the Western Cape
			
			//05
			+ "|51|5[3-4]|5[6-8]" //Free State and Northen Cape
			
			//06
			+ "|600" //Liquid Telecommunications (South Africa)
			+ "|60[1-2]" //TelkomSA (8.ta)
			+ "|60[3-5]" //MTN
			+ "|60[6-9]" //Vodacom
//			+ "|61[0-3]" //Cell C
//			+ "|614" //8ta (now Telkom Mobile)
//			+ "|615" //8ta (now Telkom Mobile)
//			+ "|61[8-9]|62[0-4]" //Cell C
			+ "|61" //Cell C
			+ "|62" //Cell C
			+ "|63[0-5]" //MTN
			+ "|63[6-7]" //Vodacom
			+ "|640" //MTN
			+ "|64[1-5]" //Cell C
			+ "|64[6-9]" //Vodacom
			+ "|65[0-4]" //Cell C
			+ "|65[5-7]" //MTN
			+ "|65[8-9]" //TelkomSA (8.ta)
			+ "|66" //Vodacom
			+ "|67[0-2]" //TelkomSA (8.ta)
			+ "|67[3-5]" //Vodacom
			+ "|67[6-9]" //TelkomSA (8.ta)
			+ "|68[0-5]" //TelkomSA (8.ta)
			+ "|68[6-9]" //MTN
			+ "|690" //MTN
			+ "|69[1-9]" //TelkomSA (8.ta)
			
			//07
			+ "|710" //MTN
			+ "|71[1-6]" //Vodacom
			+ "|71[7-9]" //MTN
			+ "|72" //Vodacom
			+ "|73" //MTN
			+ "|74" //Cell C
			+ "|741" //Virgin Mobile
			+ "|75" // Purple Mobile South Africa
			+ "|76" //Vodacom
			+ "|78" //MTN
			+ "|79" //Vodacom
			
			//08
			+ "|80" //FreeCall
			+ "|810" //MTN
			+ "|81[1-5]" //Telkom Mobile (8ta)
			+ "|816" //Rain //WBS Mobile (should be noted that Vodacom and MTN both have terminated SMS interconnect with WBS so SMS messages to/from this number range are /dev/null'ed)
			+ "|817" //Telkom Mobile
			+ "|818" //Vodacom
			+ "|819" //Telkom Mobile
			+ "|82" //Vodacom
			+ "|83" //MTN
			+ "|84" //Cell C
			+ "|85" //USAL license holders - Vodacom and MTN have some prefixes out of this range for their USAL offerings
			+ "|86" //Sharecall, MaxiCall and premium-rate services, calls can be routed to regional offices automatically
			+ "|860" //Sharecall Land line callers pay local call, called party pays long distance if applicable
			+ "|861" //MaxiCall caller always pay long distance for call even if routed to local office
			+ "|862[2-9]" //Premium rate caller pays increasing rate linked to last digit
			//08622, 086294: Competition lines caller always pay premium rate
			+ "|867[1-4]" //Information services caller always pay increasing rate linked to last digit
			+ "|87" //Value-added services (VoIP among others)
			+ "|88" //Pagers and Telkom CallAnswer voicemail
			+ "|89" //Maxinet, for polls and radio call-in services
		+ ")"
		+ "(\\d{6,7})$";
	
	private static final Pattern SA_TELEPHONE_NUMBER_REGEXP_PATTERN = Pattern.compile(SA_TELEPHONE_NUMBER_REGEXP);

	@Override
	public boolean isValid(String value) {
		// TODO Auto-generated method stub
		if (LOGGER.isLoggable(Level.INFO)) {
			LOGGER.log(Level.INFO, String.format("Validating potential value of an SA Telephone Number '%s'.", value));
		}
		
		if (Strings.isNullOrEmpty(value) ||
			value.length() < 10 || 
			!SA_TELEPHONE_NUMBER_REGEXP_PATTERN.matcher(value).matches()
		) {
			LOGGER.log(Level.SEVERE, "Invalid value provided (cannot be a SA Telephone/Mobile Number)");
			return false;
		}
		
		return true;
	}
}
