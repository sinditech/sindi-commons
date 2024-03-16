/**
 * 
 */
package za.co.sindi.commons.io;

/**
 * @author Buhake Sindi
 * @since 03 February 2024
 */
public class UncheckedException extends RuntimeException {

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public UncheckedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public UncheckedException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public UncheckedException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
}
