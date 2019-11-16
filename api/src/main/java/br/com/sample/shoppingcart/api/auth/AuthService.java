/* 	 
 * AuthService.java  
 *
 * This is a free software.
 */
package br.com.sample.shoppingcart.api.auth;

/**
 * Interface of business class of auth.
 * 
 * @author Manoel Veríssimo dos Santos Neto
 */
public interface AuthService {

	/**
	 * Authenticates the user in applying a temporary access token.
	 * 
	 * @param authTO
	 * @return
	 */
	CredentialTO login(final AuthTO authTO);

	/**
	 * Generates a new access token through the refresh token entered.
	 *
	 * @param refreshTO
	 * @return
	 */
	public CredentialTO refresh(final RefreshTO refreshTO);

	/**
	 * Returns the user information according to the 'token'.
	 *
	 * @param token
	 * @return
	 */
	CredentialTO getInfoByToken(final String token);
}
