/**
 * 
 */
package za.co.sindi.commons.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.spec.EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import za.co.sindi.commons.net.ssl.PEMReader;
import za.co.sindi.commons.net.ssl.PKCS8PrivateKeyReader;

/**
 * @author Buhake Sindi
 * @since 18 January 2024
 */
public class SSLUtils {

	private SSLUtils() {
		throw new AssertionError("Private Constructor");
	}
	
	public static KeyStore createEmptyKeyStore() throws GeneralSecurityException, IOException {
		KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType()); // JKS
		keyStore.load(null, null);
		return keyStore;
	}
	
	public static Certificate loadCertificate(final File file) throws IOException, CertificateException {
		PEMReader reader = new PEMReader(file);
		CertificateFactory certificateFactory = CertificateFactory.getInstance("X509");
	    return certificateFactory.generateCertificate(new ByteArrayInputStream(reader.getContent()));
	}
	
	/**
	 * Generate a private key object from a PKCS#8 private key file.
	 * 
	 * @param file
	 * @return a {@link PrivateKey} representation from the loaded file.
	 * @throws IOException 
	 * @throws GeneralSecurityException 
	 */
	public static PrivateKey loadPKCS8PrivateKey(final File file) throws IOException, GeneralSecurityException {
		PKCS8PrivateKeyReader reader = new PKCS8PrivateKeyReader(file);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(reader.getContent());
		return keyFactory.generatePrivate(privateKeySpec);
	}
	
//	/**
//	 * Generate a private key object from a PKCS#1 private key file.
//	 * 
//	 * @param file
//	 * @return a {@link PrivateKey} representation from the loaded file.
//	 * @throws IOException 
//	 * @throws GeneralSecurityException 
//	 */
//	public static PrivateKey loadPKCS1PrivateKey(final File file) throws IOException, GeneralSecurityException {
//		PKCS1PrivateKeyReader reader = new PKCS1PrivateKeyReader(file);
//		DerInputStream derReader = new DerInputStream(reader.getContent());
//		DerValue[] seq = derReader.getSequence(0);
//
//        if (seq.length < 9) {
//            throw new GeneralSecurityException("Could not parse a PKCS1 private key.");
//        }
//
//        // skip version seq[0];
//        BigInteger modulus = seq[1].getBigInteger();
//        BigInteger publicExp = seq[2].getBigInteger();
//        BigInteger privateExp = seq[3].getBigInteger();
//        BigInteger prime1 = seq[4].getBigInteger();
//        BigInteger prime2 = seq[5].getBigInteger();
//        BigInteger exp1 = seq[6].getBigInteger();
//        BigInteger exp2 = seq[7].getBigInteger();
//        BigInteger crtCoef = seq[8].getBigInteger();
//		
//		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//		RSAPrivateKeySpec privateKeySpec = new RSAPrivateCrtKeySpec(modulus, publicExp, privateExp, prime1, prime2, exp1, exp2, crtCoef);
//		return keyFactory.generatePrivate(privateKeySpec);
//	}
	
	public static SSLSocketFactory createClientCertificateSSLSocketFactory(final File clientCertificateFile, final File privateKeyFile, final boolean withTrust) throws GeneralSecurityException, IOException {
		Certificate clientCertificate = loadCertificate(clientCertificateFile);
		PrivateKey privateKey = loadPKCS8PrivateKey(privateKeyFile);
		
		KeyStore keyStore = createEmptyKeyStore();
//		keyStore.setCertificateEntry("alias", clientCertificate);
		keyStore.setCertificateEntry("client-cert", clientCertificate);
        keyStore.setKeyEntry("client-key", privateKey, "".toCharArray(), new Certificate[]{clientCertificate});
		
		KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
		keyManagerFactory.init(keyStore, "".toCharArray());
	    KeyManager[] keyManagers = keyManagerFactory.getKeyManagers();
	    
	    TrustManager[] trustManagers = null;
	    if (withTrust) {
		    TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		    trustManagerFactory.init(keyStore);
	    	trustManagers = trustManagerFactory.getTrustManagers();
	    }
	    
	    return createSSLSocketFactory("TLS", keyManagers, trustManagers, null);
	}
	
	public static SSLSocketFactory createSSLSocketFactory(final String sslProtocol, final KeyManager[] keyManagers, final TrustManager[] trustManagers, final SecureRandom secureRandom) throws GeneralSecurityException {
    	SSLContext sslContext = SSLContext.getInstance(sslProtocol);
    	sslContext.init(keyManagers, trustManagers, secureRandom);
    	return sslContext.getSocketFactory();
	}
	
	public static void main(String[] args) throws NoSuchAlgorithmException {
		System.out.println(KeyStore.getDefaultType());
		System.out.println(KeyManagerFactory.getDefaultAlgorithm());
		System.out.println(TrustManagerFactory.getDefaultAlgorithm());
		System.out.println(SSLContext.getDefault().getProtocol());
	}
}
