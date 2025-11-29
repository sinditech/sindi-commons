/**
 * 
 */
package za.co.sindi.commons.concurrent;

import java.util.Objects;
import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.function.Function;

/**
 * @author Buhake Sindi
 * @since 29 October 2025
 */
public class TransformingPublisher<T, R> implements Publisher<T> {
	
	private final Flow.Publisher<T> upstream;
    private final Function<? super T, ? extends R> mapper;

	/**
	 * @param upstream
	 * @param mapper
	 */
	public TransformingPublisher(Publisher<T> upstream, Function<? super T, ? extends R> mapper) {
		super();
		this.upstream = Objects.requireNonNull(upstream, "Upstream is required.");
		this.mapper = Objects.requireNonNull(mapper, "A transforming function is required.");
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void subscribe(Subscriber<? super T> subscriber) {
		// TODO Auto-generated method stub
		upstream.subscribe(new TransformingSubscriber(subscriber, mapper));
	}
	
	private static final class TransformingSubscriber<T, R> implements Subscriber<T> {
		
		private final Flow.Subscriber<? super R> downstream;
        private final Function<? super T, ? extends R> mapper;
        private Flow.Subscription upstreamSubscription;

		/**
		 * @param downstream
		 * @param mapper
		 */
		public TransformingSubscriber(Subscriber<? super R> downstream, Function<? super T, ? extends R> mapper) {
			super();
			this.downstream = Objects.requireNonNull(downstream);
			this.mapper = Objects.requireNonNull(mapper);
		}

		@Override
		public void onSubscribe(Subscription subscription) {
			// TODO Auto-generated method stub
			this.upstreamSubscription = subscription;
			downstream.onSubscribe(subscription);
		}

		@Override
		public void onNext(T item) {
			// TODO Auto-generated method stub
			try {
				downstream.onNext(mapper.apply(item));
			} catch (Throwable t) {
				upstreamSubscription.cancel();
				onError(t);
			}
		}

		@Override
		public void onError(Throwable throwable) {
			// TODO Auto-generated method stub
			downstream.onError(throwable);
		}

		@Override
		public void onComplete() {
			// TODO Auto-generated method stub
			downstream.onComplete();
		}
	}
}
