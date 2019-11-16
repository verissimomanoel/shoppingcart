/*
 * BusinessException.java
 *
 * This is a free software.
 */
package br.com.sample.shoppingcart.api.exception;

import br.com.sample.shoppingcart.api.util.Util;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Default class to business exceptions.
 * 
 * @author Manoel Veríssimo dos Santos Neto
 */
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 7986864620634914985L;

	private boolean concat;
	private MessageCode code;
	private Object[] parameters;
	private MessageResponse response;

	/**
	 * Constructor of class.
	 * 
	 * @param code
	 * @param concat
	 * @param parameters
	 */
	public BusinessException(final MessageCode code, Boolean concat, final Object... parameters) {
		this.code = code;
		this.concat = concat;
		this.parameters = parameters;
	}

	/**
	 * Constructor of class.
	 *
	 * @param code
	 * @param parameters
	 */
	public BusinessException(final MessageCode code, final Object... parameters) {
		this(code, Boolean.TRUE, parameters);
	}

	/**
	 * Constructor of class.
	 *
	 * @param code
	 */
	public BusinessException(final MessageCode code) {
		this.code = code;
	}

	/**
	 * Constructor of class.
	 *
	 * @param e
	 */
	public BusinessException(final Throwable e) {
		super(e);
	}

	/**
	 * Constructor of class.
	 * 
	 * @param response
	 */
	public BusinessException(MessageResponse response) {
		this.response = response;
	}

	/**
	 * Constructor of class.
	 * 
	 * @param field
	 */
	public BusinessException(FieldResponse field) {
		this.response = new MessageResponse();
		this.response.addAttribute(field);
	}

	/**
	 * @return the response
	 */
	public MessageResponse getResponse() {
		return response;
	}

	/**
	 * @return the concat
	 */
	public boolean isConcat() {
		return concat;
	}

	/**
	 * @return the code
	 */
	public MessageCode getCode() {
		return code;
	}

	/**
	 * @return the parameters
	 */
	public Object[] getParameters() {
		return parameters;
	}

	/**
	 * @return the hasParameters
	 */
	public boolean hasParameters() {
		return parameters != null && parameters.length > 0;
	}

	/**
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	public String getMessage() {
		String message = super.getMessage();

		if (StringUtils.isEmpty(super.getMessage())) {
			List<String> params = new ArrayList<String>();

			if (code != null) {
				params.add("code: " + code);
			}

			params.add("concat: " + concat);

			if (hasParameters()) {
				String paramsConcat = Util.getCollectionAsString(Arrays.asList(parameters));
				params.add("parameters: [" + paramsConcat + "]");
			}

			message = "{";
			message += Util.getCollectionAsString(params);
			message += "}";
		}

		return message;
	}
}
