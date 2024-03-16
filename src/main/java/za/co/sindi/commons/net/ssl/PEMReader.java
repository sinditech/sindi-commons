/**
 * 
 */
package za.co.sindi.commons.net.ssl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

/**
 * Read and decode a certificate file.
 * 
 * @author Buhake Sindi
 * @since 18 January 2024
 */
public class PEMReader extends SSLFileReader {
	
	private static final String BEGIN_CERTIFICATE_HEADER = "-----BEGIN CERTIFICATE-----";
	private static final String END_CERTIFICATE_HEADER = "-----END CERTIFICATE-----";
	
	public PEMReader(final File file) throws FileNotFoundException {
		this(new FileReader(file));
	}
	
	public PEMReader(final Reader reader) {
		super(reader, BEGIN_CERTIFICATE_HEADER, END_CERTIFICATE_HEADER);
	}
//	
//	public static void main(String... args) throws IOException {
//		PEMReader reader = new PEMReader(new InputStreamReader(new FileInputStream(new File("C:/Users/buhake.sindi/Downloads/Test_Whitelisting Certification/ccert.medikredit.co.za.pem"))));
//		System.out.println(reader.getContent().length);
//	}
}
