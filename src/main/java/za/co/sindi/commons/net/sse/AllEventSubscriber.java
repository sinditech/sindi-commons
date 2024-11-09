package za.co.sindi.commons.net.sse;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

/**
 * @author Buhake Sindi
 * @since 09 November 2024
 */
public class AllEventSubscriber implements Subscriber<Event> {
	
	private Subscription subscription;
	
	private final List<Event> events = new CopyOnWriteArrayList<>();
	private final AtomicBoolean isComplete = new AtomicBoolean(false);
    private final AtomicReference<Throwable> throwableAtomicReference = new AtomicReference<>();

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
	public void onNext(Event item) {
		// TODO Auto-generated method stub
		events.add(item);
		subscription.request(1);
	}

	/* (non-Javadoc)
	 * @see java.util.concurrent.Flow.Subscriber#onError(java.lang.Throwable)
	 */
	@Override
	public void onError(Throwable throwable) {
		// TODO Auto-generated method stub
		throwableAtomicReference.set(throwable);
	}

	/* (non-Javadoc)
	 * @see java.util.concurrent.Flow.Subscriber#onComplete()
	 */
	
	@Override
	public void onComplete() {
		// TODO Auto-generated method stub
		isComplete.set(true);
	}
	
	public Stream<Event> getEventStream() {
		return events.stream();
	}
}
