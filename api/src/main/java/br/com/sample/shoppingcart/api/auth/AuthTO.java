/* 	 
 * AuthTO.java
 *
 * This is a free software.
 */
package br.com.sample.shoppingcart.api.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Transfer object class responsible for auth.
 * 
 * @author Manoel Veríssimo dos Santos Neto
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
public class AuthTO implements Serializable {

	private static final long serialVersionUID = 2416597510043185992L;

	@NotNull
	@ApiModelProperty(value = "Login of user (Grant Type - Password)")
	private String login;

	@NotNull
	@ApiModelProperty(value = "Password of user (Grant Type - Password)")
	private String password;
}
