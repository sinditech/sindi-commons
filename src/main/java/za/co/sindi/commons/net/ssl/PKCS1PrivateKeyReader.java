/**
 * 
 */
package za.co.sindi.commons.net.ssl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

/**
 * Reads and decode a PKCS#1 format private key file.
 * 
 * @author Buhake Sindi
 * @since 18 January 2024
 */
public class PKCS1PrivateKeyReader extends SSLFileReader {
	
	private static final String BEGIN_RSA_PRIVATE_KEY_HEADER = "-----BEGIN RSA PRIVATE KEY-----";
	private static final String END_RSA_PRIVATE_KEY_HEADER = "-----END RSA PRIVATE KEY-----";
	
	public PKCS1PrivateKeyReader(final File file) throws FileNotFoundException {
		this(new FileReader(file));
	}
	
	public PKCS1PrivateKeyReader(final Reader reader) {
		super(reader, BEGIN_RSA_PRIVATE_KEY_HEADER, END_RSA_PRIVATE_KEY_HEADER);
	}
}
