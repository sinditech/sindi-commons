package za.co.sindi.commons.net.sse;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

/**
 * @author Buhake Sindi
 * @since 09 November 2024
 */
public class AllEventsEventHandler implements EventHandler {
	
	private final List<Event> events = new CopyOnWriteArrayList<>();
	private final AtomicBoolean isComplete = new AtomicBoolean(false);
    private final AtomicReference<Throwable> throwableAtomicReference = new AtomicReference<>();

	/* (non-Javadoc)
	 * @see za.co.sindi.commons.net.sse.SSEEventHandler#onEvent(za.co.sindi.commons.net.sse.Event)
	 */
	@Override
	public void onEvent(Event event) {
		// TODO Auto-generated method stub
		events.add(event);
	}

	/* (non-Javadoc)
	 * @see java.util.concurrent.Flow.Subscriber#onError(java.lang.Throwable)
	 */
	@Override
	public void onError(Throwable throwable) {
		// TODO Auto-generated method stub
		throwableAtomicReference.set(throwable);
	}

	public Stream<Event> getEventStream() {
		return events.stream();
	}
	
	public boolean iComplete() {
		return isComplete.get();
	}
	
	public Throwable getError() {
		return throwableAtomicReference.get();
	}
}
