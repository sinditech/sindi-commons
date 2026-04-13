package za.co.sindi.commons.concurrent;

import java.util.Objects;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.function.Function;

/**
 * @author Buhake Sindi
 * @since 29 October 2025
 */
public class TransformingSubscriber<T, R> implements Subscriber<T> {
	
	private final Subscriber<? super R> downstream;
    private final Function<? super T, ? extends R> mapper;

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
		downstream.onSubscribe(subscription);
	}

	@Override
	public void onNext(T item) {
		// TODO Auto-generated method stub
		boolean done = false;
		R converted = null;
		try {
			converted = mapper.apply(item);
			done = true;
		} catch (Throwable t) {
			done = false;
			onError(t);
		}
		
		if (done) downstream.onNext(converted);
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
