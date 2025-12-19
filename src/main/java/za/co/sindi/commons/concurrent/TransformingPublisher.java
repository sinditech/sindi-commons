/**
 * 
 */
package za.co.sindi.commons.concurrent;

import java.util.Objects;
import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;
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
}
