/* 	 
 * CredentialTO.java  
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

import java.util.UUID;

/**
 * Transfer object class responsible for Credential user.
 * 
 * @author Manoel Veríssimo dos Santos Neto
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
public class CredentialTO {

	@ApiModelProperty(value = "Id of User")
	private String userId;

	@ApiModelProperty(value = "Name of User")
	private String name;

	@ApiModelProperty(value = "Login of User")
	private String login;

	@ApiModelProperty(value = "Access Token")
	private String accessToken;

	@ApiModelProperty(value = "Expire in of Access Token")
	private Long accessExpiresIn;

	@ApiModelProperty(value = "Refresh Token")
	private String refreshToken;

	@ApiModelProperty(value = "Expire in of Refresh Token")
	private Long refreshExpiresIn;
}
