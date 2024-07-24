package za.co.sindi.commons.net.http;

import java.io.UncheckedIOException;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Buhake Sindi
 * @since 23 July 2024
 */
public class InetAddressUtils {

	private InetAddressUtils() {
		throw new AssertionError("Private constructor.");
	}
	
	/**
     * Checks whether the parameter is a valid IPv4 address
     *
     * @param host the address string to check for validity
     * @return true if the input parameter is a valid IPv4 address
     */
    public static boolean isIPv4Address(final String host) {
        return getByName(host) instanceof Inet4Address;
    }
    
    /**
     * Checks whether the parameter is a valid IPv6 address.
     *
     * @param host the address string to check for validity
     * @return true if the input parameter is a valid IPv6 address
     */
    public static boolean isIPv6Address(final String host) {
    	return getByName(host) instanceof Inet6Address;
    }
    
    /**
     * Get the {@link InetAddress} address from the provided host name.
     *
     * @param host the host address string
     * @return the {@link InetAddress}
     */
    public static InetAddress getByName(final String host) {
        try {
			return InetAddress.getByName(host);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			throw new UncheckedIOException(e);
		}
    }
}
