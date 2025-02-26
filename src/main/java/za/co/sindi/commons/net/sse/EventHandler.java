package za.co.sindi.commons.net.sse;

/**
 * Interface for handling events and errors. Implementations can process received
 * events and handle any errors that occur during the SSE/WebSocket connection.
 * 
 * @author Buhake Sindi
 * @since 23 Febraury 2025
 */
public interface EventHandler {

	/**
	 * Called when an SSE event is received.
	 * @param event the received SSE event containing id, type, and data
	 */
	void onEvent(Event event);

	/**
	 * Called when an error occurs during the SSE connection.
	 * @param error the error that occurred
	 */
	void onError(Throwable error);
}
