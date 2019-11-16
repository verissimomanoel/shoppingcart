/*
 * MessageResponse.java
 *
 * This is a free software.
 */
package br.com.sample.shoppingcart.api.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Representation class of response message used on implementation of 'ExceptionHandler'.
 * 
 * @author Manoel Veríssimo dos Santos Neto
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
public class MessageResponse implements Serializable {

	private static final long serialVersionUID = 4878825827657916191L;

	@ApiModelProperty(value = "Code of Mensagem")
	private String code;

	@ApiModelProperty(value = "Status HTTP")
	private Integer status;

	@ApiModelProperty(value = "HTTP error descriptions")
	private String error;

	@ApiModelProperty(value = "Business message")
	private String message;

	@ApiModelProperty(value = "Message parameters")
	private Object[] parameters;

	@ApiModelProperty(value = "Validation attributes")
	private List<FieldResponse> attributes;

	/**
	 * Add instance of {@link FieldResponse}.
	 * 
	 * @param field
	 */
	public void addAttribute(final FieldResponse field) {
		if (attributes == null) {
			attributes = new ArrayList<FieldResponse>();
		}
		attributes.add(field);
	}

	/**
	 * Checks for fields with message code.
	 * 
	 * @return
	 */
	public boolean hasFieldCode() {
		boolean hasFiel = Boolean.FALSE;

		if (attributes != null && attributes.size() > 0) {
			hasFiel = attributes.stream().filter(field -> {
				return field.getCode() != null;
			}).count() > BigDecimal.ZERO.longValue();
		}
		return hasFiel;
	}
}
