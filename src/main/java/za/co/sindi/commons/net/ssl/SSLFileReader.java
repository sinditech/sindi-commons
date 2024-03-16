/**
 * 
 */
package za.co.sindi.commons.net.ssl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Base64;

import za.co.sindi.commons.utils.CloseUtils;

/**
 * @author Buhake Sindi
 * @since 18 January 2024
 */
public abstract class SSLFileReader {
	
	private final String beginHeaderKey;
	private final String endHeaderKey;
	
	private BufferedReader reader;
	private byte[] content;
	
	protected SSLFileReader(final Reader reader, final String beginHeaderKey, final String endHeaderKey) {
		if (reader != null) {
			if (reader instanceof BufferedReader)
				this.reader = (BufferedReader)reader;
			else this.reader = new BufferedReader(reader);
		}
		
		this.beginHeaderKey = beginHeaderKey;
		this.endHeaderKey = endHeaderKey;
	}

	public byte[] getContent() throws IOException {
		if (content == null || content.length == 0) {
			loadContent();
		}
		
		return content;
	}
	
	private void loadContent() throws IOException {
		boolean beginHeaderFound = false;
		boolean endHeaderFound = false;
		StringBuffer sb = new StringBuffer();
		int lineNumber = 0;
		
		try {
			while (true) {
				String line = reader.readLine();
				if (line == null) break;
				
				line = line.trim(); //Just in case....
				lineNumber++;
				if (line.isEmpty()) {
					if (endHeaderFound) break;
					else continue;
				}
				
				if (beginHeaderKey.equals(line)) {
					if (beginHeaderFound) throw new IllegalStateException("Begin header found on line " + lineNumber);
					beginHeaderFound = true;
					continue;
				}
				else if (endHeaderKey.equals(line)) {
					if (!beginHeaderFound) throw new IllegalStateException("End header found without begin header on line " + lineNumber);
					endHeaderFound = true;
					continue;
				} else if (beginHeaderFound && !endHeaderFound) {
					sb.append(line);
				}
			}
		} finally {
			CloseUtils.closeQuietly(reader);
		}
		
		content = Base64.getDecoder().decode(sb.toString().getBytes());
	}
}
