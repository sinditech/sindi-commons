/**
 * 
 */
package za.co.sindi.commons.utils;

import java.io.Closeable;
import java.io.IOException;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.NamingException;

/**
 * This utility class will contain all methods that contain a <code>close()</code> method.
 * 
 * @author Buhake Sindi
 * @since 10 August 2014
 *
 */
public final class CloseUtils {

	private CloseUtils() {
		throw new AssertionError("Private constructor.");
	}
	
	public static final void close(AutoCloseable o) throws Exception {
		if (o != null) {
			o.close();
		}
	}
	
	public static final void close(Closeable o) throws IOException {
		if (o != null) {
			o.close();
		}
	}
	
	public static final void close(Context context) throws NamingException {
		if (context != null) {
			context.close();
		}
	}
	
	public static final void closeQuietly(Closeable o) {
		try {
			close(o);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Logger.getLogger(IOUtils.class.getName()).warning("Unable to close " + o.getClass().getName());
		}
	}
	
	public static final void closeQuietly(AutoCloseable o) {
		try {
			close(o);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Logger.getLogger(IOUtils.class.getName()).warning("Unable to close " + o.getClass().getName());
		}
	}
	
	public static final void closeQuietly(Context context) throws NamingException {
		try {
			close(context);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			Logger.getLogger(IOUtils.class.getName()).warning("Unable to close " + context.getClass().getName());
		}
	}
}
