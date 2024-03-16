/**
 * 
 */
package za.co.sindi.commons.net;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

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
    private List<String> pathSegments;
    private String encodedQuery;
    private Map<String, String> queryParameters;
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
        this(new URI(string), null);
    }

    /**
     * Construct an instance from the provided URI.
     * @param uri
     */
    public URIBuilder(final URI uri) {
        this(uri, null);
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
//        this.pathSegments = parsePath(uri.getRawPath(), this.charset != null ? this.charset : StandardCharsets.UTF_8);
        this.encodedQuery = uri.getRawQuery();
//        this.queryParams = parseQuery(uri.getRawQuery(), this.charset != null ? this.charset : StandardCharsets.UTF_8);
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
	 * @param pathSegments the pathSegments to set
	 */
	public URIBuilder setPathSegments(List<String> pathSegments) {
		this.pathSegments = pathSegments;
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
	 * @param queryParameters the queryParameters to set
	 */
	public URIBuilder setQueryParameters(Map<String, String> queryParameters) {
		this.queryParameters = queryParameters;
		return this;
	}

	/**
	 * @param query the query to set
	 */
	public URIBuilder setQuery(String query) {
		this.query = query;
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
}
