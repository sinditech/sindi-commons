/**
 * 
 */
package za.co.sindi.commons.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author Buhake Sindi
 * @since 13 November 2026
 */
public final class Charsets {

	private Charsets() {
		throw new AssertionError("Private constructor.");
	}
	
	/** 
	 * Detects UTF-8, UTF-16 and UTF-32 (BOM or the null-byte pattern of the first character); defaults to UTF-8.
	 * 
	 * @param in the input stream
	 * @throws IOException should there be an I/O issue while detecting charset. 
	 */
    public static Charset detect(InputStream in) throws IOException {
    	PushbackInputStream pb = new PushbackInputStream(in, 4);
        byte[] b = new byte[4];
        int n = 0;
        while (n < 4) {
            int r = pb.read(b, n, 4 - n);
            if (r < 0) break;
            n += r;
        }
        Charset cs = StandardCharsets.UTF_8;
        int skip = 0;
        int b0 = b[0] & 0xFF, b1 = b[1] & 0xFF, b2 = b[2] & 0xFF, b3 = b[3] & 0xFF;
        if (n >= 4 && b0 == 0 && b1 == 0 && b2 == 0xFE && b3 == 0xFF) { cs = Charset.forName("UTF-32BE"); skip = 4; }
        else if (n >= 4 && b0 == 0xFF && b1 == 0xFE && b2 == 0 && b3 == 0) { cs = Charset.forName("UTF-32LE"); skip = 4; }
        else if (n >= 3 && b0 == 0xEF && b1 == 0xBB && b2 == 0xBF) { skip = 3; }
        else if (n >= 2 && b0 == 0xFE && b1 == 0xFF) { cs = StandardCharsets.UTF_16BE; skip = 2; }
        else if (n >= 2 && b0 == 0xFF && b1 == 0xFE) { cs = StandardCharsets.UTF_16LE; skip = 2; }
        else if (n >= 4 && b0 == 0 && b1 == 0 && b2 == 0 && b3 != 0) { cs = Charset.forName("UTF-32BE"); }
        else if (n >= 4 && b0 != 0 && b1 == 0 && b2 == 0 && b3 == 0) { cs = Charset.forName("UTF-32LE"); }
        else if (n >= 2 && b0 == 0 && b1 != 0) { cs = StandardCharsets.UTF_16BE; }
        else if (n >= 2 && b0 != 0 && b1 == 0) { cs = StandardCharsets.UTF_16LE; }
        if (n > skip) pb.unread(b, skip, n - skip);
        return cs;
    }
}
