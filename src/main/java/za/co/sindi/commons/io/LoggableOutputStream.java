/**
 * 
 */
package za.co.sindi.commons.io;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Bienfait Sindi
 * @since 17 April 2014
 *
 */
public class LoggableOutputStream extends OutputStream {

	private final Logger LOGGER;
	private final Level LEVEL;
	private StringWriter writer = null;
	private boolean closed = false;
	
	/**
	 * @param logger
	 * @param level
	 */
	public LoggableOutputStream(final Logger logger, final Level level) {
		super();
		if (logger == null) {
			throw new IllegalArgumentException("No java.util.logging.Logger has been provided.");
		}
		
		if (level == null) {
			throw new IllegalArgumentException("No java.util.logging.Level has been provided.");
		}
		
		LOGGER = logger;
		LEVEL = level;
		writer = new StringWriter();
		closed = false;
	}

	/* (non-Javadoc)
	 * @see java.io.OutputStream#write(int)
	 */
	@Override
	public void write(int b) throws IOException {
		// TODO Auto-generated method stub
		writer.write(b);
	}

	/* (non-Javadoc)
	 * @see java.io.OutputStream#flush()
	 */
	@Override
	public synchronized void flush() throws IOException {
		// TODO Auto-generated method stub
		super.flush();
		ensureOpen();
		writer.flush();
		if (isLoggable()) {
			LOGGER.log(LEVEL, writer.toString());
		}
	}

	/* (non-Javadoc)
	 * @see java.io.OutputStream#close()
	 */
	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		flush();
		super.close();
		closed = true;
	}
	
	public boolean isLoggable() {
		return LOGGER.isLoggable(LEVEL);
	}
	
	private void ensureOpen() {
		if (closed) {
			throw new IllegalStateException("Stream is closed.");
		}
	}
}
