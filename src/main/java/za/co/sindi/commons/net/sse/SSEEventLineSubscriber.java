/**
 * 
 */
package za.co.sindi.commons.net.sse;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

/**
 * The implementation of the <a href="https://html.spec.whatwg.org/multipage/server-sent-events.html">Server-Sent Events Specification.</a>
 * 
 * @author Buhake Sindi
 * @since 19 December 2025
 */
public class SSEEventLineSubscriber implements Subscriber<List<ByteBuffer>> {

	private StringBuilder currentLine = new StringBuilder();
	
	private Subscription subscription;
	private SSEEventSubscriber delegate;
	
	/**
	 * @param sseEventHandler
	 */
	public SSEEventLineSubscriber(EventHandler sseEventHandler) {
		this.delegate = new SSEEventSubscriber(sseEventHandler);
	}
	
	@Override
	public void onSubscribe(Subscription subscription) {
		// TODO Auto-generated method stub
		this.subscription = subscription;
		delegate.onSubscribe(subscription);
	}

	@Override
	public void onNext(List<ByteBuffer> buffers) {
		// TODO Auto-generated method stub
		try {
            for (ByteBuffer buffer : buffers) {
                String chunk = StandardCharsets.UTF_8.decode(buffer).toString();
                processChunk(chunk);
            }
//            subscription.request(1); // Request next chunk
        } catch (Exception e) {
            subscription.cancel();
            onError(e);
        }
	}

	@Override
	public void onError(Throwable throwable) {
		// TODO Auto-generated method stub
		delegate.onError(throwable);
	}

	@Override
	public void onComplete() {
		// TODO Auto-generated method stub
		delegate.onComplete();
	}
	
	private void processChunk(String chunk) {
        for (char c : chunk.toCharArray()) {
            if (c == '\n') {
            	delegate.onNext(currentLine.toString());
                currentLine.setLength(0);
            } else if (c != '\r') {
                currentLine.append(c);
            }
        }
    }
}
