/**
 * 
 */
package za.co.sindi.commons.net.ssl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

/**
 * Reads and decode a PKCS#8 format private key file.
 * 
 * @author Buhake Sindi
 * @since 18 January 2024
 */
public class PKCS8PrivateKeyReader extends SSLFileReader {
	
	private static final String BEGIN_PRIVATE_KEY_HEADER = "-----BEGIN PRIVATE KEY-----";
	private static final String END_PRIVATE_KEY_HEADER = "-----END PRIVATE KEY-----";
	
	public PKCS8PrivateKeyReader(final File file) throws FileNotFoundException {
		this(new FileReader(file));
	}
	
	public PKCS8PrivateKeyReader(final Reader reader) {
		super(reader, BEGIN_PRIVATE_KEY_HEADER, END_PRIVATE_KEY_HEADER);
	}
//	
//	public static void main(String... args) throws IOException {
//		PKCS8PrivateKeyReader reader = new PKCS8PrivateKeyReader(new InputStreamReader(new FileInputStream(new File("C:/Users/buhake.sindi/Downloads/Test_Whitelisting Certification/ccert.medikredit.co.za.key"))));
//		System.out.println(reader.getContent().length);
//	}
}
