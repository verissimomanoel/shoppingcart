/*
 * AuthenticationProvider.java
 *
 * This is a free software.
 */
package br.com.sample.shoppingcart.api.security;

import br.com.sample.shoppingcart.api.auth.CredentialTO;
import org.springframework.security.core.Authentication;

/**
 * Inteface responsible to provide an instance of {@link Authentication} with user credentials.
 * 
 * @author Manoel Veríssimo dos Santos Neto.
 */
public interface AuthenticationProvider {

	/**
	 * Return the intance of {@link Authentication} with informations on 'token' reported.
	 * 
	 * @param token
	 * @return
	 */
	CredentialTO getAuthentication(final String token);
}
