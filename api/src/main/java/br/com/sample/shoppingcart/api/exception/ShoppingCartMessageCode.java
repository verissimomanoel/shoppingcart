/*
 * AuthMessageCode.java
 *
 * This is a free software.
 */
package br.com.sample.shoppingcart.api.exception;

/**
 * Enum with the codes of exceptions and business messages.
 * 
 * @author Manoel Veríssimo dos Santos Neto S/A.
 */
public enum ShoppingCartMessageCode implements MessageCode {
	ERROR_EMPTY_FIELD("value.empty", 400),

	ERROR_UNEXPECTED("ME001", 500),
	ERROR_REQUIRED_FIELDS("ME002", 400),
	ERROR_LOGIN_PASSWORD_INVALID("ME003", 400),
	ERROR_TOKEN_INVALID("ME004", 401),
	ERROR_NO_FOUND("ME005", 404),
	ERROR_USER_ALREADY_EXISTS("ME006", 400),
	ERROR_FILTER_REQUIRED("ME007", 400),
	ERROR_NO_CART_FOUND("ME008", 404),
	ERROR_CART_ALREADY_EXISTS("ME009", 400),
	ERROR_ITEM_VALUE("ME010", 400),
	ERROR_ITEM_DONT_EXISTS_ON_CART("ME011", 400);

	private final String code;

	private final Integer status;

	/**
	 * Constructor of class.
	 * 
	 * @param code
	 * @param status
	 */
	ShoppingCartMessageCode(final String code, final Integer status) {
		this.code = code;
		this.status = status;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @see Enum#toString()
	 */
	@Override
	public String toString() {
		return code;
	}
}
