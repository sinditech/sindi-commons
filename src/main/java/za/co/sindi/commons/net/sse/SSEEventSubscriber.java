package za.co.sindi.commons.net.sse;

import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

/**
 * The implementation of the <a href="https://html.spec.whatwg.org/multipage/server-sent-events.html">Server-Sent Events Specification.</a>
 * 
 * @author Buhake Sindi
 * @since 09 November 2024
 */
public class SSEEventSubscriber implements Subscriber<String>  {

	private static final String UTF8_BOM = "\uFEFF";
	private static final String DEFAULT_EVENT_TYPE = "message";
	private static final String COLON = ":";
	private static final String LF = "\n";
	private static final char SPACE = ' ';
	
	private String comment;
	private String lastEventId = "";
	private String eventType = "";
	private StringBuilder dataBuffer = new StringBuilder();
	
	private boolean commentFound = false;
	private boolean fireCommentEvent = true;
	
	private Subscription subscription;
	private EventHandler sseEventHandler;
	
	/**
	 * @param sseEventHandler
	 */
	public SSEEventSubscriber(EventHandler sseEventHandler) {
		this(sseEventHandler, false);
	}
	
	/**
	 * @param sseEventHandler
	 * @param fireCommentEvent
	 */
	public SSEEventSubscriber(EventHandler sseEventHandler, boolean fireCommentEvent) {
		super();
		this.sseEventHandler = sseEventHandler;
		this.fireCommentEvent = fireCommentEvent;
	}

	/* (non-Javadoc)
	 * @see java.util.concurrent.Flow.Subscriber#onSubscribe(java.util.concurrent.Flow.Subscription)
	 */
	@Override
	public void onSubscribe(Subscription subscription) {
		// TODO Auto-generated method stub
		(this.subscription = subscription).request(1);
	}

	/* (non-Javadoc)
	 * @see java.util.concurrent.Flow.Subscriber#onNext(java.lang.Object)
	 */
	@Override
	public void onNext(String item) {
		// TODO Auto-generated method stub
		String input = removeLeadingBOM(item).trim();
		if (input.isEmpty()) {
			if (commentFound) {
				if (fireCommentEvent) sseEventHandler.onEvent(new CommentEvent(comment));
			} else {
				if (eventType.isEmpty()) eventType = DEFAULT_EVENT_TYPE;
				String data = dataBuffer.toString();
				sseEventHandler.onEvent(new DataMessageEvent(eventType, removeTrailingLineFeed(data), lastEventId));
				eventType = "";
				dataBuffer.setLength(0);
			}
		} else {
			commentFound = input.startsWith(COLON);
			if (commentFound) {
				comment = input.substring(1).trim();
			} else {
				String field = input;
				String value = "";
				
				if (input.contains(COLON)) {
					int colonPos = input.indexOf(COLON);
					field = input.substring(0, colonPos);
					value = removeLeadingSpace(input.substring(colonPos + 1));
				}
				
				processField(field, value);
			}
		}
		
		//Finally
		subscription.request(1);
	}

	/* (non-Javadoc)
	 * @see java.util.concurrent.Flow.Subscriber#onError(java.lang.Throwable)
	 */
	@Override
	public void onError(Throwable throwable) {
		// TODO Auto-generated method stub
		sseEventHandler.onError(throwable);
	}

	/* (non-Javadoc)
	 * @see java.util.concurrent.Flow.Subscriber#onComplete()
	 */
	@Override
	public void onComplete() {
		// TODO Auto-generated method stub
		if (dataBuffer.length() > 0) {
			if (eventType.isEmpty()) eventType = DEFAULT_EVENT_TYPE;
			String data = dataBuffer.toString();
			sseEventHandler.onEvent(new DataMessageEvent(eventType, removeTrailingLineFeed(data), lastEventId));
			eventType = "";
			dataBuffer.setLength(0);
		}
	}
	
	private String removeLeadingBOM(final String input) {
		return input.startsWith(UTF8_BOM) ? input.substring(UTF8_BOM.length()) : input; 
	}
	
	private String removeLeadingSpace(final String input) {
		return input.charAt(0) == SPACE ? input.substring(1) : input; 
	}
	
	private String removeTrailingLineFeed(final String input) {
		return input.endsWith(LF) ? input.substring(0, input.length() - 1) : input; 
	}
	
	private void processField(final String field, final String value) {
		switch(field) {
			case "event" : eventType = value;
			break;
			
			case "data" : dataBuffer.append(value).append(LF);
			break;
			
			case "id" : 
				if (!value.contains("\0")) lastEventId = value;
			break;
			
			case "retry" : ;
			break;
		}
	}
}
