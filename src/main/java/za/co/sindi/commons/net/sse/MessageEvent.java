package za.co.sindi.commons.net.sse;

/**
 * @author Buhake Sindi
 * @since 09 November 2024
 */
public interface MessageEvent extends Event {

	public String getType();
	public String getData();
	public String getLastEventId();
}
