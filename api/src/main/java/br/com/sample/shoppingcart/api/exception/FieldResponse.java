/*
 * FieldResponse.java
 *
 * This is a free software.
 */
package br.com.sample.shoppingcart.api.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Representation class of response attributes. This is used on implementation of 'ExceptionHandler'.
 * 
 * @author Manoel Veríssimo dos Santos Neto
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
public class FieldResponse implements Serializable {

	private static final long serialVersionUID = -807504480597471148L;

	@JsonIgnore
	@ApiModelProperty(hidden = true)
	private MessageCode code;

	@ApiModelProperty(value = "Name of attribute")
	private String attribute;

	@ApiModelProperty(value = "Description of attribute")
	private String description;

	/**
	 * Constructor of class.
	 * 
	 * @param attribute
	 * @param description
	 */
	public FieldResponse(final String attribute, final String description) {
		this.attribute = attribute;
		this.description = description;
	}

	/**
	 * Constructor of class.
	 * 
	 * @param attribute
	 * @param code
	 */
	public FieldResponse(final String attribute, final MessageCode code) {
		this.attribute = attribute;
		this.code = code;
	}

}
