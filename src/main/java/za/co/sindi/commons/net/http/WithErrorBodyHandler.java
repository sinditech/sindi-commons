/**
 * 
 */
package za.co.sindi.commons.net.http;

import java.net.http.HttpResponse.BodyHandler;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.http.HttpResponse.BodySubscriber;
import java.net.http.HttpResponse.BodySubscribers;
import java.net.http.HttpResponse.ResponseInfo;

import za.co.sindi.commons.util.Either;

/**
 * @author Buhake Sindi
 * @since 26 January 2024
 */
public class WithErrorBodyHandler<T> implements BodyHandler<Either<T, String>> {
	
	private final BodyHandler<T> bodyHandler;
	
	/**
	 * @param bodyHandler (the {@link BodyHandler} that will handle your response. This will always be returned as left of the {@link Either} value.
	 */
	public WithErrorBodyHandler(BodyHandler<T> bodyHandler) {
		super();
		this.bodyHandler = bodyHandler;
	}

	@Override
	public BodySubscriber<Either<T, String>> apply(ResponseInfo responseInfo) {
		// TODO Auto-generated method stub
		int code = responseInfo.statusCode() / 100;
		if (code == 4 || code == 5) {
			//This is now error handling mode....
			return BodySubscribers.mapping(BodyHandlers.ofString().apply(responseInfo),
                    (r) -> Either.right(r));
		}
		
		return BodySubscribers.mapping(bodyHandler.apply(responseInfo),
                (l) -> Either.left(l));
	}
}
