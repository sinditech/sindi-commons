/**
 * 
 */
package za.co.sindi.commons.util;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Implementation of Either&lt;L, R&gt; via a pair of Optionals which explicitly
 * reject null values.
 * 
 * The solution was taken from this StackOverflow question and answer <a href="https://stackoverflow.com/questions/26162407/is-there-an-equivalent-of-scalas-either-in-java-8">here</a>.
 * 
 * @author Buhake Sindi
 * @since 20 January 2024
 */
public class Either<L, R> implements Serializable {

	public static <L,R> Either<L,R> left(L value) {
		return new Either<>(Optional.of(value), Optional.empty());
	}
	
	public static <L,R> Either<L,R> right(R value) {
	    return new Either<>(Optional.empty(), Optional.of(value));
	}
	
	private final Optional<L> left;
    private final Optional<R> right;
    
	/**
	 * @param left
	 * @param right
	 */
	private Either(final Optional<L> left, final Optional<R> right) {
		super();
		if (left.isEmpty() && right.isEmpty()) {
			throw new IllegalArgumentException("Both left and right are empty.");
		}
		
		this.left = left;
		this.right = right;
	}
	
	public boolean isLeftPresent() {
		return left.isPresent();
	}
	
	public L getLeft() {
		return left.get();
	}
	
	public boolean isRightPresent() {
		return right.isPresent();
	}
	
	public R getRight() {
		return right.get();
	}
    
	public <T> T map(Function<? super L, ? extends T> leftFunction, Function<? super R, ? extends T> rightFunction) {
		Objects.requireNonNull(leftFunction, "No left function provided.");
		Objects.requireNonNull(rightFunction, "No right function provided.");
	    return left.<T>map(leftFunction).orElseGet(()->right.map(rightFunction).get());
	}
	
	public <T> Either<T,R> mapLeft(Function<? super L, ? extends T> leftFunction) {
		Objects.requireNonNull(leftFunction, "No left function provided.");
	    return new Either<>(left.map(leftFunction), right);
	}
	
	public <T> Either<L,T> mapRight(Function<? super R, ? extends T> rightFunction) {
		Objects.requireNonNull(rightFunction, "No right function provided.");
	    return new Either<>(left, right.map(rightFunction));
	}
	
	public void apply(Consumer<? super L> leftConsumer, Consumer<? super R> rightConsumer) {
		Objects.requireNonNull(leftConsumer, "No left consumer provided.");
		Objects.requireNonNull(rightConsumer, "No right consumer provided.");
	    left.ifPresent(leftConsumer);
	    right.ifPresent(rightConsumer);
	}
	
	public void forEach(Consumer<? super L> leftConsumer, Consumer<? super R> rightConsumer) {
		Objects.requireNonNull(leftConsumer, "No left consumer provided.");
		Objects.requireNonNull(rightConsumer, "No right consumer provided.");
	    this.left.ifPresent(leftConsumer);
	    this.right.ifPresent(rightConsumer);
	}
}
