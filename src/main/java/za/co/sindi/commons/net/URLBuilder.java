/**
 * 
 */
package za.co.sindi.commons.net;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import za.co.sindi.commons.utils.Preconditions;
import za.co.sindi.commons.utils.Strings;
import za.co.sindi.commons.utils.URLEncoderUtils;

/**
 * @author Buhake Sindi
 * @since 20 May 2014
 *
 */
public class URLBuilder {
	
	private static final Charset DEFAULT_CHARSET = Charset.forName("US-ASCII");

	private Charset encodingCharset;
	private String scheme;
//	private String user;
//	private String password;
	private String userInfo;
	private String host;
	private int port = -1;
	private String path;
	private Map<String, String[]> pathParametersMap = new LinkedHashMap<String, String[]>();
	private Map<String, String[]> queryParametersMap = new LinkedHashMap<String, String[]>();
	private String fragment;
	
	/**
	 * @param encodingCharsetName the encodingCharsetName to set
	 */
	public URLBuilder setEncodingCharsetName(String encodingCharsetName) {
		return setEncodingCharset(Charset.forName(encodingCharsetName));
	}
	
	/**
	 * @param encodingCharset the encodingCharset to set
	 */
	public URLBuilder setEncodingCharset(Charset encodingCharset) {
		this.encodingCharset = encodingCharset;
		return this;
	}

	/**
	 * @param scheme the scheme to set
	 */
	public URLBuilder setScheme(String scheme) {
		if (!Strings.isNullOrEmpty(scheme)) {
			if (scheme.endsWith("://")) {
				this.scheme = scheme.substring(0, scheme.length() - 3);
			} else if (scheme.endsWith(":")) {
				this.scheme = scheme.substring(0, scheme.length() - 1);
			}
			
			if (!this.scheme.matches("[a-zA-Z0-9+.-]")) {
				throw new IllegalArgumentException("An invalid character was found in scheme '" + this.scheme + "').");
			}
		}
		
		return this;
	}
	
	/**
	 * @param userInfo the userInfo to set
	 */
	public URLBuilder setUserInfo(String userInfo) {
		this.userInfo = userInfo;
		return this;
	}

	public URLBuilder setUserInfo(String user, String password) {
		StringBuilder sb = new StringBuilder();
		if (!Strings.isNull(user)) {
			sb.append(user);
		}
		
		if (!Strings.isNull(password)) {
			sb.append(":").append(password);
		}
		
		return setUserInfo(sb.toString());
	}

	/**
	 * @param host the host to set
	 */
	public URLBuilder setHost(String host) {
		this.host = host;
		return this;
	}

	/**
	 * @param port the port to set
	 */
	public URLBuilder setPort(int port) {
		Preconditions.checkArgument(port >= 0, "HTTP port may never be negative.");
//		if (port < 0) {
//			throw new IllegalArgumentException("HTTP port may never be negative.");
//		}
		
		this.port = port;
		return this;
	}

	/**
	 * @param path the path to set
	 */
	public URLBuilder setPath(String path) {
		if (!Strings.isNullOrEmpty(path)) {
			if (path.startsWith("/")) {
				this.path = path.substring(1);
			} else {
				this.path = path;
			}
		}
		
		return this;
	}
	
	public URLBuilder addPathParameters(String key, String... values) {
		if (key != null && values != null) {
			Set<String> valueSet;
			if (pathParametersMap.containsKey(key)) {
				valueSet = new TreeSet<String>(List.of(pathParametersMap.get(key)));
			} else {
				valueSet = new TreeSet<String>();
			}
			
			for (String value : values) {
				if (value != null) {
					valueSet.add(value);
				}
			}
			
			pathParametersMap.put(key, valueSet.toArray(new String[valueSet.size()]));
		}
		
		return this;
	}
	
	public URLBuilder addQueryParameters(String key, String... values) {
		if (key != null && values != null) {
			Set<String> valueSet;
			if (queryParametersMap.containsKey(key)) {
				valueSet = new TreeSet<String>(List.of(queryParametersMap.get(key)));
			} else {
				valueSet = new TreeSet<String>();
			}
			
			for (String value : values) {
				if (value != null) {
					valueSet.add(value);
				}
			}
			
			queryParametersMap.put(key, valueSet.toArray(new String[valueSet.size()]));
		}
		
		return this;
	}

	/**
	 * @param fragment the fragment to set
	 */
	public URLBuilder setFragment(String fragment) {
		if (!Strings.isNullOrEmpty(fragment)) {
			if (fragment.startsWith("#")) {
				this.fragment = fragment.substring(1);
			} else {
				this.fragment = fragment;
			}
		}
		
		return this;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if (encodingCharset == null) {
			encodingCharset = DEFAULT_CHARSET;
		}
		
		StringBuilder urlBuilder = new StringBuilder();
		
		if (!Strings.isNullOrEmpty(scheme)) {
			urlBuilder.append(URLEncoderUtils.encodeScheme(scheme, encodingCharset)).append(":");
		}
		
		urlBuilder.append("//");
//		if (!Strings.isNullOrEmpty(user) && !Strings.isNullOrEmpty(password)) {
//			urlBuilder.append(URLEncoderUtils.encodeUserInfo(user, encodingCharset)).append(":").append(URLEncoderUtils.encodeUserInfo(password, encodingCharset)).append("@");
//		}
		if (!Strings.isNullOrEmpty(userInfo)) {
			urlBuilder.append(URLEncoderUtils.encodeUserInfo(userInfo, encodingCharset)).append("@");
		}
		
		if (!Strings.isNullOrEmpty(host)) {
			urlBuilder.append(URLEncoderUtils.encodeHost(host, encodingCharset));
		}
		
		if (port > -1) {
			urlBuilder.append(":").append(port);
		}
		
		if (!Strings.isNullOrEmpty(path)) {
			urlBuilder.append("/").append(URLEncoderUtils.encodePath(path, encodingCharset));
		}
		
		if (!pathParametersMap.isEmpty()) {
			urlBuilder.append(";");
			urlBuilder.append(encodePathParameters(encodingCharset));
		}
		
		if (!queryParametersMap.isEmpty()) {
			urlBuilder.append("?");
			urlBuilder.append(encodeQueryParameters(encodingCharset));
		}
		
		if (!Strings.isNullOrEmpty(fragment)) {
			urlBuilder.append("#");
			urlBuilder.append(URLEncoderUtils.encodeFragment(fragment, encodingCharset));
		}
		
		return urlBuilder.toString();
	}
	
	public URI toURI() {
		if (encodingCharset == null) {
			encodingCharset = DEFAULT_CHARSET;
		}
		
//		return new URI(scheme, userInfo, host, port, path + ";" + encodePathParameters(encodingCharset), encodeQueryParameters(encodingCharset), fragment);
//		return new URI(toString());
		return URI.create(toString());
	}
	
	private String encodePathParameters(Charset encodingCharset) {
		StringBuilder sb = new StringBuilder();
		for (Entry<String, String[]> entry : pathParametersMap.entrySet()) {
			if (sb.length() > 0) {
				sb.append(";");
			}
			
			sb.append(URLEncoderUtils.encodePath(entry.getKey(), encodingCharset)).append("=");
			for (int i = 0; i < entry.getValue().length; i++) {
				if (i > 0) {
					sb.append(",");
				}
				
				sb.append(URLEncoderUtils.encodePath(entry.getValue()[i], encodingCharset));
			}
		}
		
		return sb.toString();
	}
	
	private String encodeQueryParameters(Charset encodingCharset) {
		StringBuilder sb = new StringBuilder();
		for (Entry<String, String[]> entry : queryParametersMap.entrySet()) {
			if (sb.length() > 0) {
				sb.append("&");
			}
			
			String encodedKey = URLEncoderUtils.encodeQuery(entry.getKey(), encodingCharset);
			String[] mapValues = entry.getValue();
			for (int i = 0; i < mapValues.length; i++) {
				sb.append(encodedKey).append("=").append(URLEncoderUtils.encodeQuery(mapValues[i], encodingCharset));
			}
		}
		
		return sb.toString();
	}
}
