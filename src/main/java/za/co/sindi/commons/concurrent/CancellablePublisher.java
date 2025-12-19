/**
 * 
 */
package za.co.sindi.commons.concurrent;

import java.util.Objects;
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

/**
 * @author Buhake Sindi
 * @since 17 December 2025
 */
public class CancellablePublisher<T> implements Publisher<T> {

	private final Publisher<T> downstream;
	private final Runnable cancellable;
	
	/**
	 * @param downstream
	 * @param cancellable
	 */
	public CancellablePublisher(Publisher<T> downstream, Runnable cancellable) {
		super();
		this.downstream = Objects.requireNonNull(downstream, "A publisher is required.");
		this.cancellable = cancellable;
	}


	@Override
	public void subscribe(Subscriber<? super T> subscriber) {
		// TODO Auto-generated method stub
		downstream.subscribe(new CancellableSubscriber<T>() {

			@Override
			public void onSubscribe(Subscription subscription) {
				// TODO Auto-generated method stub
				subscriber.onSubscribe(new CancellableSubscription(subscription, this));
			}

			@Override
			public void onNext(T item) {
				// TODO Auto-generated method stub
				subscriber.onNext(item);
			}

			@Override
			public void onError(Throwable throwable) {
				// TODO Auto-generated method stub
				subscriber.onError(throwable);
			}

			@Override
			public void onComplete() {
				// TODO Auto-generated method stub
				subscriber.onComplete();
			}

			@Override
			public void cancel() {
				// TODO Auto-generated method stub
				cancellable.run();
			}
		});
	}
}
