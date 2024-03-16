/**
 * 
 */
package za.co.sindi.commons.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.function.Predicate;

/**
 * @author Buhake Sindi
 * @since 28 April 2015
 *
 */
public final class Throwables {

	private Throwables() {
		throw new AssertionError("Private constructor.");
	}
	
	public static Throwable getCause(Throwable throwable, Predicate<Throwable> stopPredicate) {
		Throwable cause = throwable;
		while (/*cause != null &&*/ !stopPredicate.test(cause)) {
			cause = cause.getCause();
		}
		
		return cause;
	}
	
	public static Throwable getRootCause(Throwable throwable) {
		Throwable rootCause = throwable;
		while (rootCause != null) {
			Throwable cause = rootCause.getCause();
			if (cause == null) break;
			rootCause = cause;
		}
		
		return rootCause;
//		return getCause(throwable, new Predicate<Throwable>() {
//
//			/* (non-Javadoc)
//			 * @see za.co.sindi.common.functional.Predicate#apply(java.lang.Object)
//			 */
//			@Override
//			public boolean apply(Throwable input) {
//				// TODO Auto-generated method stub
//				if (input == null) {
//					return false;
//				}
//				
//				return (input.getCause() == null);
//			}
//		});
	}
	
	public static String getStackTraceAsString(Throwable throwable) {
		StringWriter writer = new StringWriter();
		throwable.printStackTrace(new PrintWriter(writer));
		return writer.toString();
	}
}
