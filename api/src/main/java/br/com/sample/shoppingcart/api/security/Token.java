/*
 * Token.java
 *
 * This is a free software.
 */
package br.com.sample.shoppingcart.api.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * Class of representation of 'JWT Token'.
 * 
 * @author Manoel Veríssimo dos Santos Neto
 */
@Getter
@Setter
@NoArgsConstructor
public class Token {

	private Long expiry;

	private String value;

	private Date expiresAt;

	/**
	 * Constructor of class.
	 * 
	 * @param value
	 */
	public Token(final String value) {
		this.value = value;
	}

	/**
	 * Constructor of class.
	 * 
	 * @param value
	 * @param expiry
	 * @param expiresAt
	 */
	public Token(final String value, final Long expiry, final Date expiresAt) {
		this.value = value;
		this.expiry = expiry;
		this.expiresAt = expiresAt;
	}
}
