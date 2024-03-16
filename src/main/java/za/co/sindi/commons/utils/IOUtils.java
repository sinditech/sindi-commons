/**
 * 
 */
package za.co.sindi.commons.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.lang.AssertionError;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.Charset;

/**
 * @author Buhake Sindi
 * @since 20 September 2014
 *
 */
public final class IOUtils {

	private static final int DEFAULT_BUFFER_SIZE = 8192;
	
	private IOUtils() {
		throw new AssertionError("Private constructor.");
	}
	
	public static final int copy(InputStream input, OutputStream output) throws IOException {
		byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
		int totalBytesRead = 0;
		int bytesRead = 0;
		
		while (-1 != (bytesRead = input.read(buffer))) {
			output.write(buffer, 0, bytesRead);
			totalBytesRead += bytesRead;
		}
		
		return totalBytesRead;
	}
	
	public static final int copy(Reader reader, Writer writer) throws IOException {
		char[] buffer = new char[DEFAULT_BUFFER_SIZE];
		int totalBytesRead = 0;
		int bytesRead = 0;
		
		while (-1 != (bytesRead = reader.read(buffer))) {
			writer.write(buffer, 0, bytesRead);
			totalBytesRead += bytesRead;
		}
		
		return totalBytesRead;
	}
	
	public static final int copy(InputStream input, Writer writer) throws IOException {
		return copy(input, writer, (Charset)null);
	}
	
	public static final int copy(InputStream input, Writer writer, String charsetName) throws IOException {
		Reader reader = (charsetName == null || charsetName.isEmpty()) ? new InputStreamReader(input) : new InputStreamReader(input, charsetName);
		return copy(reader, writer);
	}
	
	public static final int copy(InputStream input, Writer writer, Charset charset) throws IOException {
		Reader reader = (charset == null)? new InputStreamReader(input) : new InputStreamReader(input, charset);
		return copy(reader, writer);
	}
	
	public static final int fastCopy(InputStream input, OutputStream output) throws IOException {
		return fastCopy(Channels.newChannel(input), Channels.newChannel(output));
	}
	
	public static final int fastCopy(ReadableByteChannel input, WritableByteChannel output) throws IOException {
		int totalLength = 0;
		
		try {
			ByteBuffer buffer = ByteBuffer.allocate(DEFAULT_BUFFER_SIZE);
			while (input.read(buffer) != -1) {
				//Flip buffer
				buffer.flip();
				//Write to destination
				totalLength += output.write(buffer);
				//Compact
				buffer.compact();
			}
			
			//In case we have remainder
			buffer.flip();
			while (buffer.hasRemaining()) {
				//Write to output
				totalLength += output.write(buffer);
			}
		} finally {
			CloseUtils.close(input);
			CloseUtils.close(output);
		}
		
		return totalLength;
	}
	
	public static final byte[] toByteArray(InputStream input) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		fastCopy(input, output);
		return output.toByteArray();
	}
	
	public static final String toString(InputStream input) throws IOException {
		return toString(input, (Charset)null);
	}
	
	public static final String toString(InputStream input, String charsetName) throws IOException {
		StringWriter writer = new StringWriter();
		copy(input, writer, charsetName);
		return writer.toString();
	}
	
	public static final String toString(InputStream input, Charset charset) throws IOException {
		StringWriter writer = new StringWriter();
		copy(input, writer, charset);
		return writer.toString();
	}
	
	public static final String toString(Reader reader) throws IOException {
		StringWriter writer = new StringWriter();
		copy(reader, writer);
		return writer.toString();
	}
}
