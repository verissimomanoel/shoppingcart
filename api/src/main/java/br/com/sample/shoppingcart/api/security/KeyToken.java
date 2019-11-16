/*
 * KeyToken.java
 *
 * This is a free software.
 */
package br.com.sample.shoppingcart.api.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

/**
 * Represent the key of JWT - Json Web Tokens.
 * 
 * @author Manoel Veríssimo dos Santos Neto
 */
@Getter
@Setter
@NoArgsConstructor
public class KeyToken {

	private byte[] secret;

	private String issuer;

	private Long expiry;

	/**
     * Constructor of class.
	 * 
	 * @param secret
	 * @param issuer
	 * @param expiry
	 */
	public KeyToken(final String secret, final String issuer, final Long expiry) {

		if (StringUtils.isEmpty(secret) || StringUtils.isEmpty(issuer) || expiry == null) {
			throw new IllegalArgumentException("Required fields not entered");
		}

		this.expiry = expiry;
		this.issuer = issuer;
		this.secret = secret.getBytes();
	}
}