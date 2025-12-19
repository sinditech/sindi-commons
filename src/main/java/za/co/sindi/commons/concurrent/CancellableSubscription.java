/**
 * 
 */
package za.co.sindi.commons.concurrent;

import java.util.Objects;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

/**
 * @author Buhake Sindi
 * @since 17 December 2025
 */
public class CancellableSubscription implements Subscription {

	private static final Logger LOGGER = Logger.getLogger(CancellableSubscription.class.getName());
	private final Subscription upstream;
	private final Cancellable cancellable;
	private final AtomicBoolean cancelled = new AtomicBoolean(false);
	
	/**
	 * @param upstream
	 * @param cancellable
	 */
	public CancellableSubscription(Subscription upstream, Cancellable cancellable) {
		super();
		this.upstream = Objects.requireNonNull(upstream, "A Flow subscription is required.");
		this.cancellable = cancellable;
	}

	@Override
	public void request(long n) {
		// TODO Auto-generated method stub
		if (!cancelled.get()) {
			upstream.request(n);
		}
	}

	@Override
	public void cancel() {
		// TODO Auto-generated method stub
		if (cancelled.compareAndSet(false, true)) {
			LOGGER.fine("Subscription cancelled! Executing custom handler.");
			if (cancellable != null) {
				cancellable.cancel();;
			}
			upstream.cancel();
		}
	}
}
