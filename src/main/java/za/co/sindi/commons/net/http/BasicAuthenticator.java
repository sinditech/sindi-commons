/**
 * 
 */
package za.co.sindi.commons.net.http;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

/**
 * @author Buhake Sindi
 * @since 24 January 2024
 */
public class BasicAuthenticator extends Authenticator {

	private String userName;
    private String password;
    
	/**
	 * @param userName
	 * @param password
	 */
	public BasicAuthenticator(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}

	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		// TODO Auto-generated method stub
		return new PasswordAuthentication(userName, password.toCharArray());
	}
}
