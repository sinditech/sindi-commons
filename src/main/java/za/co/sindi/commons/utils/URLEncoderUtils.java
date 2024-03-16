/**
 * 
 */
package za.co.sindi.commons.utils;

import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.BitSet;
import java.util.Map;
import java.util.Objects;

/**
 * This utility class specify URL encoding functions to conform to <a href="http://www.ietf.org/rfc/rfc3986">RFC 3986 - URI Generic Syntax</a>.
 * 
 * @author Buhake Sindi
 * @since 17 July 2014
 *
 */
public final class URLEncoderUtils {
	
	/**
     * The default HTML form content type.
     */
    public static final String CONTENT_TYPE = "application/x-www-form-urlencoded";

	private static final byte PERCENT_CHARACTER = '%';
	private static final int RADIX = 16;
	
	private static final BitSet SCHEME = new BitSet(256);
	private static final BitSet USERINFO = new BitSet(256);
	private static final BitSet HOST = new BitSet(256);
	private static final BitSet PATH = new BitSet(256);
	private static final BitSet QUERY = new BitSet(256);
	private static final BitSet FRAGMENT = new BitSet(256);
	
	private URLEncoderUtils() {
		throw new AssertionError("Private constructor.");
	}
	
	static {
		//DIGIT
		BitSet digit = new BitSet(256);
		digit.set('0');
		digit.set('1');
		digit.set('2');
		digit.set('3');
		digit.set('4');
		digit.set('5');
		digit.set('6');
		digit.set('7');
		digit.set('8');
		digit.set('9');
		
		//ALPHA
		BitSet alpha = new BitSet(256);
		alpha.set('a');
		alpha.set('b');
		alpha.set('c');
		alpha.set('d');
		alpha.set('e');
		alpha.set('f');
		alpha.set('g');
		alpha.set('h');
		alpha.set('i');
		alpha.set('j');
		alpha.set('k');
		alpha.set('l');
		alpha.set('m');
		alpha.set('n');
		alpha.set('o');
		alpha.set('p');
		alpha.set('q');
		alpha.set('r');
		alpha.set('s');
		alpha.set('t');
		alpha.set('u');
		alpha.set('v');
		alpha.set('w');
		alpha.set('x');
		alpha.set('y');
		alpha.set('z');
		alpha.set('A');
		alpha.set('B');
		alpha.set('C');
		alpha.set('D');
		alpha.set('E');
		alpha.set('F');
		alpha.set('G');
		alpha.set('H');
		alpha.set('I');
		alpha.set('J');
		alpha.set('K');
		alpha.set('L');
		alpha.set('M');
		alpha.set('N');
		alpha.set('O');
		alpha.set('P');
		alpha.set('Q');
		alpha.set('R');
		alpha.set('S');
		alpha.set('T');
		alpha.set('U');
		alpha.set('V');
		alpha.set('W');
		alpha.set('X');
		alpha.set('Y');
		alpha.set('Z');
		
		//HEXDIG
		BitSet hexdig = new BitSet(256);
		hexdig.or(digit);
		hexdig.set('a');
		hexdig.set('b');
		hexdig.set('c');
		hexdig.set('d');
		hexdig.set('e');
		hexdig.set('f');
		hexdig.set('A');
		hexdig.set('B');
		hexdig.set('C');
		hexdig.set('D');
		hexdig.set('E');
		hexdig.set('F');
		
		//gen-delims
		BitSet gen_delims = new BitSet(256);
		gen_delims.set(':');
		gen_delims.set('/');
		gen_delims.set('?');
		gen_delims.set('#');
		gen_delims.set('[');
		gen_delims.set(']');
		gen_delims.set('@');
		
		//sub-delims
		BitSet sub_delims = new BitSet(256);
		sub_delims.set('!');
		sub_delims.set('$');
		sub_delims.set('&');
		sub_delims.set('\'');
		sub_delims.set('(');
		sub_delims.set(')');
		sub_delims.set('*');
		sub_delims.set('+');
		sub_delims.set(',');
		sub_delims.set(';');
		sub_delims.set('=');
		
		//pct-encoded
		BitSet pct_encoded = new BitSet(256);
		pct_encoded.set('%');
		pct_encoded.or(hexdig);
		
		//unreserved
		BitSet unreserved = new BitSet(256);
		unreserved.or(alpha);
		unreserved.or(digit);
		unreserved.set('-');
		unreserved.set('.');
		unreserved.set('_');
		unreserved.set('~');
		
		//reserved
		BitSet reserved = new BitSet(256);
		reserved.or(gen_delims);
		reserved.or(sub_delims);
		
		//pchar
		BitSet pchar = new BitSet(256);
		pchar.or(unreserved);
		pchar.or(pct_encoded);
		pchar.or(sub_delims);
		pchar.set(':');
		pchar.set('@');
		
		//segment
		BitSet segment = new BitSet(256);
		segment.or(pchar);
		
		//SCHEME
		SCHEME.or(alpha);
		SCHEME.or(digit);
		SCHEME.set('+');
		SCHEME.set('-');
		SCHEME.set('.');
		
		//USERINFO
		USERINFO.or(unreserved);
//		USERINFO.or(pct_encoded);
		USERINFO.or(sub_delims);
		USERINFO.set(':');
		
		//HOST
		HOST.or(unreserved);
//		HOST.or(pct_encoded);
		HOST.or(sub_delims);
		
		//PATH
		PATH.set('/');
		PATH.or(segment);
		
		//QUERY
		QUERY.or(pchar);
		QUERY.set('/');
		QUERY.set('?');
		
		//FRAGMENT
		FRAGMENT.or(pchar);
		FRAGMENT.set('/');
		FRAGMENT.set('?');
	}
	
	private static byte[] encode(byte[] data, BitSet reservedCharacters, boolean treatBlankAsPlus) {
		// TODO Auto-generated method stub
		if (data == null || data.length == 0) {
			return data;
		}
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		for (byte c : data) {
			if(reservedCharacters.get(c)) {
				baos.write(c);
			} else if (c == ' ' && treatBlankAsPlus) {
				baos.write('+');
			} else {
				baos.write(PERCENT_CHARACTER);
				baos.write(Character.toUpperCase(Character.forDigit((c >> 4) & 0xF, RADIX)));
				baos.write(Character.toUpperCase(Character.forDigit(c & 0xF, RADIX)));
			}
		}
		
		return baos.toByteArray();
	}
	
	private static byte[] decode(byte[] data) {
		// TODO Auto-generated method stub
		if (data == null || data.length == 0) {
			return data;
		}
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int length = data.length;
		for (int i = 0; i < length; i++) {
			if(data[i] == PERCENT_CHARACTER) {
				if ((i + 2) >= length) {
					throw new IllegalArgumentException("Invalid % encoding sequence on index " + i);
				} 

				int digit1 = Character.digit(data[i + 1], RADIX);
				int digit2 = Character.digit(data[i + 2], RADIX);
				
				if (digit1 == -1) {
					throw new IllegalArgumentException("Invalid character '" + data[i + 1] + "' on index " + (i + 1));
				}
				
				if (digit2 == -1) {
					throw new IllegalArgumentException("Invalid character '" + data[i + 2] + "' on index " + (i + 2));
				}
				
				baos.write(((digit1 << 4) | digit2) & 0xFF);
				i += 2;
			} else {
				baos.write(data[i]);
			}
		}
	
		return baos.toByteArray();
	}
	
	private static String urlEncode(String str, String charsetName, BitSet safeCharacters, boolean treatBlankAsPlus) {
		return urlEncode(str, Charset.forName(charsetName), safeCharacters, treatBlankAsPlus);
	}
	
	private static String urlEncode(String str, Charset charset, BitSet safeCharacters, boolean treatBlankAsPlus) {
//		URLEncoder encoder = new URLEncoder(safeCharacters, treatBlankAsPlus);
//		return new String(encoder.encode(str.getBytes(charset)), charset);
		return new String(encode(str.getBytes(charset), safeCharacters, treatBlankAsPlus), charset);
	}
	
	public static String urlDecode(String str, String charsetName) {
		return urlDecode(str, Charset.forName(charsetName));
	}
	
	public static String urlDecode(String str, Charset charset) {
//		URLDecoder decoder = new URLDecoder();
//		return new String(decoder.decode(str.getBytes(charset)), charset);
		return new String(decode(str.getBytes(charset)), charset);
	}
	
	public static String encodeScheme(String scheme, String charsetName) {
		return urlEncode(scheme, charsetName, SCHEME, false);
	}
	
	public static String encodeScheme(String scheme, Charset charset) {
		return urlEncode(scheme, charset, SCHEME, false);
	}
	
	public static String encodeUserInfo(String userInfo, String charsetName) {
		return urlEncode(userInfo, charsetName, USERINFO, false);
	}
	
	public static String encodeUserInfo(String userInfo, Charset charset) {
		return urlEncode(userInfo, charset, USERINFO, false);
	}
	
	public static String encodeHost(String host, String charsetName) {
		return urlEncode(host, charsetName, HOST, false);
	}
	
	public static String encodeHost(String host, Charset charset) {
		return urlEncode(host, charset, HOST, false);
	}
	
	public static String encodePath(String path, String charsetName) {
		return urlEncode(path, charsetName, PATH, false);
	}
	
	public static String encodePath(String path, Charset charset) {
		return urlEncode(path, charset, PATH, false);
	}
	
	public static String encodeQuery(String query, String charsetName) {
		return urlEncode(query, charsetName, QUERY, true);
	}
	
	public static String encodeQuery(String query, Charset charset) {
		return urlEncode(query, charset, QUERY, true);
	}
	
	public static String encodeFragment(String fragment, String charsetName) {
		return urlEncode(fragment, charsetName, FRAGMENT, false);
	}
	
	public static String encodeFragment(String fragment, Charset charset) {
		return urlEncode(fragment, charset, FRAGMENT, false);
	}
	
	public static String formatQueryParameters(Map<String, Object> parameters, final char parameterSeparator, Charset charset) {
		StringBuilder formBodyBuilder = new StringBuilder();
	    for (Map.Entry<String, Object> singleEntry : parameters.entrySet()) {
	        if (formBodyBuilder.length() > 0) {
	            formBodyBuilder.append(parameterSeparator);
	        }
	        formBodyBuilder.append(singleEntry.getKey());
	        formBodyBuilder.append("=");
	        formBodyBuilder.append(String.valueOf(Objects.requireNonNull(singleEntry.getValue())));
	    }
	    
	    return encodeQuery(formBodyBuilder.toString(), charset);
	}
	
	public static String formatQueryParametersNatively(Map<String, Object> parameters, final char parameterSeparator, Charset charset) {
		StringBuilder formBodyBuilder = new StringBuilder();
	    for (Map.Entry<String, Object> singleEntry : parameters.entrySet()) {
	        if (formBodyBuilder.length() > 0) {
	            formBodyBuilder.append(parameterSeparator);
	        }
	        formBodyBuilder.append(URLEncoder.encode(singleEntry.getKey(), charset));
	        formBodyBuilder.append("=");
	        formBodyBuilder.append(URLEncoder.encode(String.valueOf(Objects.requireNonNull(singleEntry.getValue())), charset));
	    }
	    
	    return formBodyBuilder.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(URLEncoderUtils.encodeQuery(")(*&^$%@!", StandardCharsets.UTF_8));
	}
}
