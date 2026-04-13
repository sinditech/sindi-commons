/**
 * 
 */
package za.co.sindi.commons.concurrent;

import java.util.concurrent.Flow.Publisher;
import java.util.function.Function;

/**
 * @author Buhake Sindi
 * @since 11 April 2026
 */
public final class Publishers {

	private Publishers() {
		throw new AssertionError("Private constructor.");
	}
	
	public static <T, R> Publisher<R> map(final Publisher<T> source, final Function<? super T, ? extends R> mapper) {
		
		return new TransformingPublisher<>(source, mapper);
	}
}
