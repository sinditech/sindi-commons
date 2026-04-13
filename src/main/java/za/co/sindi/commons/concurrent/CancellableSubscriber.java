/**
 * 
 */
package za.co.sindi.commons.concurrent;

import java.util.concurrent.Flow.Subscriber;

/**
 * @author Buhake Sindi
 * @since 18 December 2025
 */
public interface CancellableSubscriber<T> extends Subscriber<T> {

	/**
     * Method invoked upon cancellation by Subscription, after which no other Subscriber
     * methods are invoked by the Subscription.
     * 
     */
	public void onCancel();
}
