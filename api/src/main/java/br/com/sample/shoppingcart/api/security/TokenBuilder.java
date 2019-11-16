/*
 * TokenBuilder.java
 *
 * This is a free software.
 */
package br.com.sample.shoppingcart.api.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

/**
 * A builder of JWT tokens.
 * 
 * @author Manoel Veríssimo dos Santos Neto
 */
public class TokenBuilder {

	private final Log logger = LogFactory.getLog(getClass());

	private final KeyToken keyToken;

	private JWTCreator.Builder builder;

	/**
	 * Constructor of class.
	 *
	 * @param keyToken
	 */
	public TokenBuilder(final KeyToken keyToken) {
		this.keyToken = keyToken;
		this.builder = JWT.create();
		this.builder.withIssuedAt(new Date());
		this.builder.withIssuer(keyToken.getIssuer());
	}

	/**
	 * Add the 'Name' of user in parameters.
	 *
	 * @param name
	 * @return
	 */
	public TokenBuilder addName(final String name) {
		builder.withClaim(TokenParam.name.toString(), name);
		return this;
	}

	/**
	 * Token Type
	 *
	 * @author Manoel Veríssimo dos Santos Neto
	 */
	public enum TokenType {
		ACCESS, REFRESH, VALIDATION
	}

	/**
	 * Add any parameter of the token JWT.
	 * 
	 * @param name
	 * @param value
	 * @return
	 */
	public TokenBuilder addParam(final String name, final String value) {
		builder.withClaim(name, value);
		return this;
	}

	/**
	 * Token Params
	 *
	 * @author Manoel Veríssimo dos Santos Neto
	 */
	public enum TokenParam {
		type, name, login
	}

	/**
	 * Add the login name of user in parameters.
	 * 
	 * @param login
	 * @return
	 */
	public TokenBuilder addLogin(final String login) {
		builder.withClaim(TokenParam.login.toString(), login);
		return this;
	}

	/**
	 * Return the refresh token generate with JWT - Json Web Tokens.
	 * 
	 * @return
	 */
	public Token buildRefresh() {
		builder.withClaim(TokenParam.type.toString(), TokenType.REFRESH.toString());

		Long expiry = keyToken.getExpiry() + keyToken.getExpiry();
		Date expiresAt = getExpiresAt(TokenType.REFRESH);
		builder.withExpiresAt(expiresAt);

		Algorithm algorithm = Algorithm.HMAC256(keyToken.getSecret());
		String value = builder.sign(algorithm);

		return new Token(value, expiry, expiresAt);
	}

	/**
	 * Return the token generate with JWT - Json Web Tokens.
	 * 
	 * @return
	 */
	public Token buildAccess() {
		builder.withClaim(TokenParam.type.toString(), TokenType.ACCESS.toString());

		Long expiry = keyToken.getExpiry();
		Date expiresAt = getExpiresAt(TokenType.ACCESS);
		builder.withExpiresAt(expiresAt);

		Algorithm algorithm = Algorithm.HMAC256(keyToken.getSecret());
		String value = builder.sign(algorithm);

		return new Token(value, expiry, expiresAt);
	}

	/**
	 * Return the parameters in JWT Token.
	 * 
	 * @param token
	 * @return
	 */
	public Map<String, Claim> getClaims(final String token) {

		if (StringUtils.isEmpty(token)) {
			throw new IllegalArgumentException("The parameter 'token' is required.");
		}

		try {
			Algorithm algorithm = Algorithm.HMAC256(keyToken.getSecret());
			JWTVerifier verifier = JWT.require(algorithm).withIssuer(keyToken.getIssuer()).build();
			DecodedJWT jwt = verifier.verify(token);
			return jwt.getClaims();
		} catch (JWTVerificationException e) {
			logger.warn("Invalid Token!", e);
			return null;
		}
	}

	/**
	 * Return the expire date in JWT Token.
	 *
	 * Retorna a instância {@link Date} referente a expiração do 'Token'.
	 * 
	 * @param type
	 * @return
	 */
	private Date getExpiresAt(final TokenType type) {
		Long expiry = keyToken.getExpiry();

		if (TokenType.REFRESH.equals(type)) {
			expiry += expiry;
		}
		LocalDateTime current = LocalDateTime.now();
		current = current.plusSeconds(expiry);

		return Date.from(current.atZone(ZoneId.systemDefault()).toInstant());
	}

}