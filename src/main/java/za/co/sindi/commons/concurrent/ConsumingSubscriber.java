/**
 * 
 */
package za.co.sindi.commons.concurrent;

import java.util.Objects;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @author Buhake Sindi
 * @since 28 October 2025
 */
public class ConsumingSubscriber<T> implements Subscriber<T> {
	
	private final BiConsumer<Subscriber<? super T>, ? super T> nextConsumer;
	private final Consumer<Throwable> errorConsumer;
	private final Consumer<Void> completeConsumer;
	private Subscription subscription;
	
	/**
	 * @param nextConsumer
	 */
	public ConsumingSubscriber(BiConsumer<Subscriber<? super T>, ? super T> nextConsumer) {
		this(nextConsumer, null, null);
	}
	
	/**
	 * @param nextConsumer
	 * @param errorConsumer
	 * @param completeConsumer
	 */
	public ConsumingSubscriber(BiConsumer<Subscriber<? super T>, ? super T> nextConsumer, Consumer<Throwable> errorConsumer, Consumer<Void> completeConsumer) {
		super();
		this.nextConsumer = Objects.requireNonNull(nextConsumer, "A processing consumer is required.");
		this.errorConsumer = errorConsumer;
		this.completeConsumer = completeConsumer;
	}

	@Override
	public void onSubscribe(Subscription subscription) {
		// TODO Auto-generated method stub
		(this.subscription = subscription).request(1L);
	}

	@Override
	public void onNext(T item) {
		// TODO Auto-generated method stub
		try {
			nextConsumer.accept(this, item);
			subscription.request(1L);
		} catch (Throwable t) {
			onError(t);
		}
	}

	@Override
	public void onError(Throwable throwable) {
		// TODO Auto-generated method stub
		subscription.cancel();
		if (errorConsumer != null) errorConsumer.accept(throwable);
	}

	@Override
	public void onComplete() {
		// TODO Auto-generated method stub
		subscription.cancel();
		if (completeConsumer != null) completeConsumer.accept(null);
	}
}
