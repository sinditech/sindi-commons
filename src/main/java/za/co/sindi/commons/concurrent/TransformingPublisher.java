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
public class TransformingPublisher<T, R> implements Publisher<R> {
	
	private final Flow.Publisher<T> source;
    private final Function<? super T, ? extends R> mapper;

	/**
	 * @param source
	 * @param mapper
	 */
	public TransformingPublisher(Publisher<T> source, Function<? super T, ? extends R> mapper) {
		super();
		this.source = Objects.requireNonNull(source, "The source publisher is required.");
		this.mapper = Objects.requireNonNull(mapper, "A mapping function is required.");
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void subscribe(Subscriber<? super R> subscriber) {
		// TODO Auto-generated method stub
		source.subscribe(new TransformingSubscriber(subscriber, mapper));
	}
}
