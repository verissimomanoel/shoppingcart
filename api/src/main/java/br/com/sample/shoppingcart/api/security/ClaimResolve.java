/* 	 
 * ClaimResolve.java  
 *
 * This is a free software.
 */
package br.com.sample.shoppingcart.api.security;

import br.com.sample.shoppingcart.api.security.TokenBuilder.TokenParam;
import br.com.sample.shoppingcart.api.security.TokenBuilder.TokenType;
import com.auth0.jwt.interfaces.Claim;

import java.util.Map;

/**
 * Class resolves responsible for encapsulating the complexity of retrieving JWT
 * Token claim parameters.
 * 
 * @author Manoel Veríssimo dos Santos Neto
 */
public class ClaimResolve {
	private final Map<String, Claim> claims;
	public final static String PARAM_NAME = "name";
	public final static String PARAM_LOGIN = "login";
	public final static String PARAM_USER = "userId";

	/**
	 * Constructor classe.
	 * 
	 * @param claims
	 */
	private ClaimResolve(final Map<String, Claim> claims) {
		this.claims = claims;
	}

	/**
	 * Instance Factory {@link ClaimResolve}.
	 * 
	 * @param claims
	 * @return
	 */
	public static ClaimResolve newInstance(final Map<String, Claim> claims) {
		return new ClaimResolve(claims);
	}

	/**
	 * Checks through the claims map if the token is valid.
	 * 
	 * @return
	 */
	public boolean isValidationToken() {
		boolean valid = Boolean.FALSE;

		if (claims != null) {
			Claim claim = claims.get(TokenParam.type.toString());
			TokenType type = TokenType.valueOf(claim.asString());
			valid = TokenType.VALIDATION.equals(type);
		}
		return valid;
	}

	/**
	 * Checks through the claims map if the token is access.
	 * 
	 * @return
	 */
	public boolean isAccessToken() {
		boolean valid = Boolean.FALSE;

		if (claims != null) {
			Claim claim = claims.get(TokenParam.type.toString());
			TokenType type = TokenType.valueOf(claim.asString());
			valid = TokenType.ACCESS.equals(type);
		}
		return valid;
	}

	/**
	 * Checks through the claims map if the token is refresh.
	 * 
	 * @return
	 */
	public boolean isRefreshToken() {
		boolean valid = Boolean.FALSE;

		if (claims != null) {
			Claim claim = claims.get(TokenParam.type.toString());
			TokenType type = TokenType.valueOf(claim.asString());
			valid = TokenType.REFRESH.equals(type);
		}
		return valid;
	}

	/**
	 * Returns user id by claims.
	 * 
	 * @return
	 */
	public String getUserId() {
		String id = null;
		Claim claim = claims.get(PARAM_USER);

		if (claim != null) {
			id = claim.asString();
		}

		return id;
	}


	/**
	 * Returns user name by claims.
	 * 
	 * @return
	 */
	public String getName() {
		String name = null;
		Claim claim = claims.get(PARAM_NAME);

		if (claim != null) {
			name = claim.asString();
		}
		return name;
	}

	/**
	 * Returns user login by claims.
	 * 
	 * @return
	 */
	public String getLogin() {
		String login = null;
		Claim claim = claims.get(PARAM_LOGIN);

		if (claim != null) {
			login = claim.asString();
		}
		return login;
	}
}