/**
 * 
 */
package za.co.sindi.commons.net;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import za.co.sindi.commons.net.http.InetAddressUtils;
import za.co.sindi.commons.utils.Strings;
import za.co.sindi.commons.utils.URLEncoderUtils;

/**
 * @author Buhake Sindi
 * @since 03 February 2024
 */
public class URIBuilder {
	
	private String scheme;
    private String encodedSchemeSpecificPart;
    private String encodedAuthority;
    private String userInfo;
    private String encodedUserInfo;
    private String host;
    private int port;
    private String encodedPath;
    private boolean pathRootless;
    private List<String> pathSegments;
    private String encodedQuery;
    private Map<String, List<String>> queryParameters;
    private String query;
    private Charset charset;
    private String fragment;
    private String encodedFragment;

	/**
     * Constructs an empty instance.
     */
    public URIBuilder() {
        super();
        this.port = -1;
    }

    /**
     * Construct an instance from the string which must be a valid URI.
     *
     * @param string a valid URI in string form
     * @throws URISyntaxException if the input is not a valid URI
     */
    public URIBuilder(final String string) throws URISyntaxException {
        this(new URI(string), StandardCharsets.UTF_8);
    }

    /**
     * Construct an instance from the provided URI.
     * @param uri
     */
    public URIBuilder(final URI uri) {
        this(uri, StandardCharsets.UTF_8);
    }

    /**
     * Construct an instance from the string which must be a valid URI.
     *
     * @param string a valid URI in string form
     * @throws URISyntaxException if the input is not a valid URI
     */
    public URIBuilder(final String string, final Charset charset) throws URISyntaxException {
        this(new URI(string), charset);
    }

    /**
     * Construct an instance from the provided URI.
     * @param uri
     */
    public URIBuilder(final URI uri, final Charset charset) {
        super();
        setCharset(charset);
        digestURI(uri);
    }

    private void digestURI(final URI uri) {
        this.scheme = uri.getScheme();
        this.encodedSchemeSpecificPart = uri.getRawSchemeSpecificPart();
        this.encodedAuthority = uri.getRawAuthority();
        this.host = uri.getHost();
        this.port = uri.getPort();
        this.encodedUserInfo = uri.getRawUserInfo();
        this.userInfo = uri.getUserInfo();
        this.encodedPath = uri.getRawPath();
        this.pathRootless = uri.getRawPath() == null || !uri.getRawPath().startsWith("/");
        this.pathSegments = parsePath(uri.getRawPath(), this.charset != null ? this.charset : StandardCharsets.UTF_8);
        this.encodedQuery = uri.getRawQuery();
        this.query = uri.getQuery();
        this.queryParameters = parseQuery(uri.getRawQuery(), this.charset != null ? this.charset : StandardCharsets.UTF_8, true);
        this.encodedFragment = uri.getRawFragment();
        this.fragment = uri.getFragment();
    }

	/**
	 * @param scheme the scheme to set
	 */
	public URIBuilder setScheme(String scheme) {
		this.scheme = scheme;
		return this;
	}

	/**
	 * @param encodedSchemeSpecificPart the encodedSchemeSpecificPart to set
	 */
	public URIBuilder setEncodedSchemeSpecificPart(String encodedSchemeSpecificPart) {
		this.encodedSchemeSpecificPart = encodedSchemeSpecificPart;
		return this;
	}

	/**
	 * @param encodedAuthority the encodedAuthority to set
	 */
	public URIBuilder setEncodedAuthority(String encodedAuthority) {
		this.encodedAuthority = encodedAuthority;
		return this;
	}

	/**
	 * @param userInfo the userInfo to set
	 */
	public URIBuilder setUserInfo(String userInfo) {
		this.userInfo = userInfo;
		return this;
	}

	/**
	 * @param encodedUserInfo the encodedUserInfo to set
	 */
	public URIBuilder setEncodedUserInfo(String encodedUserInfo) {
		this.encodedUserInfo = encodedUserInfo;
		return this;
	}

	/**
	 * @param host the host to set
	 */
	public URIBuilder setHost(String host) {
		this.host = host;
		return this;
	}

	/**
	 * @param port the port to set
	 */
	public URIBuilder setPort(int port) {
		this.port = port;
		return this;
	}

	/**
	 * @param encodedPath the encodedPath to set
	 */
	public URIBuilder setEncodedPath(String encodedPath) {
		this.encodedPath = encodedPath;
		return this;
	}
	
	/**
	 * @param pathSegments the pathSegments to append to current path segments
	 */
	public URIBuilder appendPathSegments(List<String> pathSegments) {
		if (pathSegments != null && !pathSegments.isEmpty()) {
			if (this.pathSegments == null) {
				this.pathSegments = new ArrayList<>();
			}
			this.pathSegments.addAll(pathSegments);
			this.encodedSchemeSpecificPart = null;
            this.encodedPath = null;
		}
		return this;
	}

	/**
	 * @param pathSegments the pathSegments to set
	 */
	public URIBuilder setPathSegments(List<String> pathSegments) {
		appendPathSegments(pathSegments);
		return this;
	}
	
	/**
     * Sets rootless URI path (the first segment does not start with a /).
     * The value is expected to be unescaped and may contain non ASCII characters.
     *
     * @return this.
     */
	public URIBuilder setPathSegmentsRootless(final List<String> pathSegments) {
        this.pathSegments = pathSegments != null && !pathSegments.isEmpty() ? new ArrayList<>(pathSegments) : null;
        this.encodedSchemeSpecificPart = null;
        this.encodedPath = null;
        this.pathRootless = true;
        return this;
    }

	/**
	 * @param encodedQuery the encodedQuery to set
	 */
	public URIBuilder setEncodedQuery(String encodedQuery) {
		this.encodedQuery = encodedQuery;
		return this;
	}
	
	/**
	 * @param name the queryParameters name
	 * @param value the queryParameters value
	 */
	public URIBuilder addQueryParameters(String name, String value) {
		if (this.queryParameters == null) {
			this.queryParameters = new LinkedHashMap<>();
		}
		
		if (!Strings.isNullOrEmpty(name)) {
			if (queryParameters.containsKey(name)) {
				queryParameters.get(name).add(value);
			} else {
				queryParameters.put(name, Arrays.asList(value));
			}
		}
		this.encodedQuery = null;
        this.encodedSchemeSpecificPart = null;
        this.query = null;
		return this;
	}

	/**
	 * @param queryParameters the queryParameters to set
	 */
	public URIBuilder setQueryParameters(Map<String, List<String>> queryParameters) {
		this.queryParameters = queryParameters;
		this.encodedQuery = null;
        this.encodedSchemeSpecificPart = null;
        this.query = null;
		return this;
	}

	/**
	 * @param query the query to set
	 */
	public URIBuilder setQuery(String query) {
		this.query = !Strings.isNullOrEmpty(query) ? query : null;
		this.encodedQuery = null;
        this.encodedSchemeSpecificPart = null;
        this.queryParameters = null;
		return this;
	}

	/**
	 * @param charset the charset to set
	 */
	public URIBuilder setCharset(Charset charset) {
		this.charset = charset;
		return this;
	}

	/**
	 * @param fragment the fragment to set
	 */
	public URIBuilder setFragment(String fragment) {
		this.fragment = fragment;
		return this;
	}

	/**
	 * @param encodedFragment the encodedFragment to set
	 */
	public URIBuilder setEncodedFragment(String encodedFragment) {
		this.encodedFragment = encodedFragment;
		return this;
	}
	
	/**
     * Tests whether the URI is absolute.
     *
     * @return whether the URI is absolute.
     */
    public boolean isAbsolute() {
        return this.scheme != null;
    }

    /**
     * Tests whether the URI is opaque.
     *
     * @return whether the URI is opaque.
     */
    public boolean isOpaque() {
        return this.pathSegments == null && this.encodedPath == null;
    }
    
    /**
     * Tests whether the query is empty.
     *
     * @return whether the query is empty.
     */
    public boolean isQueryEmpty() {
        return (this.queryParameters == null || this.queryParameters.isEmpty()) && this.encodedQuery == null;
    }


	/**
	 * @return the scheme
	 */
	public String getScheme() {
		return scheme;
	}

	/**
	 * @return the encodedSchemeSpecificPart
	 */
	public String getSchemeSpecificPart() {
		return encodedSchemeSpecificPart;
	}

	/**
	 * @return the userInfo
	 */
	public String getUserInfo() {
		return userInfo;
	}

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @return the query
	 */
	public String getQuery() {
		return query;
	}

	/**
	 * @return the fragment
	 */
	public String getFragment() {
		return fragment;
	}
	
	/**
	 * @return the pathSegments
	 */
	public List<String> getPathSegments() {
		return pathSegments;
	}

	/**
	 * @return the queryParameters
	 */
	public Map<String, List<String>> getQueryParameters() {
		if (queryParameters == null) queryParameters = new LinkedHashMap<>();
		return Collections.unmodifiableMap(queryParameters);
	}

	/**
     * Gets the path.
     *
     * @return the path.
     */
    public String getPath() {
        if (this.pathSegments == null) {
            return null;
        }
        final StringBuilder result = new StringBuilder();
        for (final String segment : this.pathSegments) {
            result.append('/').append(segment);
        }
        return result.toString();
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		final StringBuilder sb = new StringBuilder();
        if (this.scheme != null) {
            sb.append(this.scheme).append(':');
        }
        if (this.encodedSchemeSpecificPart != null) {
            sb.append(this.encodedSchemeSpecificPart);
        } else {
            final boolean authoritySpecified;
            if (this.encodedAuthority != null) {
                sb.append("//").append(this.encodedAuthority);
                authoritySpecified = true;
            } else if (this.host != null) {
                sb.append("//");
                if (this.encodedUserInfo != null) {
                    sb.append(this.encodedUserInfo).append("@");
                } else if (this.userInfo != null) {
                    final int idx = this.userInfo.indexOf(':');
                    if (idx != -1) {
                    	sb.append(URLEncoderUtils.encodeUserInfo(this.userInfo.substring(0, idx), this.charset));
                        sb.append(':');
                        sb.append(URLEncoderUtils.encodeUserInfo(this.userInfo.substring(idx + 1), this.charset));
                    } else {
                    	URLEncoderUtils.encodeUserInfo(this.userInfo, this.charset);
                    }
                    sb.append("@");
                }
                if (InetAddressUtils.isIPv6Address(this.host)) {
                    sb.append("[").append(this.host).append("]");
                } else {
                    sb.append(URLEncoderUtils.encodeHost(this.host, this.charset));
                }
                if (this.port >= 0) {
                    sb.append(":").append(this.port);
                }
                authoritySpecified = true;
            } else {
                authoritySpecified = false;
            }
            if (this.encodedPath != null) {
                if (authoritySpecified && !Strings.isNullOrEmpty(this.encodedPath) && !this.encodedPath.startsWith("/")) {
                    sb.append('/');
                }
                sb.append(this.encodedPath);
            } else if (this.pathSegments != null) {
            	sb.append(formatPath(this.pathSegments, !authoritySpecified && this.pathRootless, this.charset));
            }
            if (this.encodedQuery != null) {
                sb.append("?").append(this.encodedQuery);
            } else if (this.queryParameters != null && !this.queryParameters.isEmpty()) {
                sb.append("?");
                sb.append(formatQuery(this.queryParameters, this.charset)); //, false);
            }  else if (this.query != null) {
                sb.append("?");
                sb.append(URLEncoderUtils.encodeQuery(this.query, this.charset));
            } 
        }
        if (this.encodedFragment != null) {
            sb.append("#").append(this.encodedFragment);
        } else if (this.fragment != null) {
            sb.append("#");
            sb.append(URLEncoderUtils.encodeFragment(this.fragment, this.charset));
        }
        return sb.toString();
	}
	
	public URI build() {
		return URI.create(toString());
	}
	
	static String formatQuery(final Map<String, List<String>> params, final Charset charset) { //, final boolean blankAsPlus) {
		StringBuilder sb = new StringBuilder();
		int i = 0;
		for (final Entry<String, List<String>> parameter : params.entrySet()) {
			if (i > 0) {
				sb.append("&");
			}
			sb.append(URLEncoderUtils.encodeQuery(parameter.getKey(), charset));
			if (parameter.getValue() != null) {
				sb.append("=");
				for (int index = 0; index < parameter.getValue().size(); index++) {
					if (index > 0) {
						sb.append(",");
					}
					sb.append(URLEncoderUtils.encodeQuery(parameter.getValue().get(index), charset));
				}
			}
			i++;
		}
		
		return sb.toString();
	}
	
	static String formatPath(final Iterable<String> segments, final boolean rootless, final Charset charset) {
		StringBuilder sb = new StringBuilder();
		int i = 0;
        for (final String segment : segments) {
            if (i > 0 || !rootless) {
            	sb.append("/");
            }
            sb.append(URLEncoderUtils.encodePath(segment, charset));
            i++;
        }
		
		return sb.toString();
    }
	
	static List<String> parsePath(final CharSequence s, final Charset charset) {
        if (s == null) {
            return null;
        }
        final List<String> segments = new ArrayList<>();
        int startPos = 0;
        int index = 0;
        for (; index < s.length(); index++) {
        	if (s.charAt(index) == '/') {
        		if (index > 0) {
        			String segment = URLEncoderUtils.urlDecode(s.subSequence(startPos, index).toString(), charset, false);
        			segments.add(segment);
        		}
        		startPos = index + 1;
        	}
        }
        
        String segment = URLEncoderUtils.urlDecode(s.subSequence(startPos, index).toString(), charset, false);
		segments.add(segment);
        return segments;
    }
	
	static Map<String, List<String>> parseQuery(final CharSequence s, final Charset charset, final boolean plusAsBlank) {
        if (s == null) {
            return null;
        }
        
        final Map<String, List<String>> params = new LinkedHashMap<>();
        String[] pairs = s.toString().split("&");
        for (String pair : pairs) {
        	int idx = pair.indexOf("=");
        	String name = URLEncoderUtils.urlDecode(pair.substring(0, idx), charset, true);
        	String[] values = URLEncoderUtils.urlDecode(pair.substring(idx + 1), charset, true).split(",");
        	params.put(name, Arrays.asList(values));
        }
        
        return params;
    }
}
