/**
 * 
 */
package za.co.sindi.commons.concurrent;

import java.util.concurrent.Flow.Subscriber;

/**
 * @author Buhake Sindi
 * @since 18 December 2025
 */
public interface CancellableSubscriber<T> extends Subscriber<T>, Cancellable {

}
