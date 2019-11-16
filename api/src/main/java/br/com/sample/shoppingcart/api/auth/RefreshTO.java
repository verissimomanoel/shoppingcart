/* 	 
 * RefreshTO.java
 *
 * This is a free software.
 */
package br.com.sample.shoppingcart.api.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * Transfer object class responsible for refresh.
 *
 * @author Manoel Veríssimo dos Santos Neto
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RefreshTO implements Serializable {

	private static final long serialVersionUID = -3902923000057875891L;

	@NotEmpty
	@ApiModelProperty(value = "Refresh Token")
	private String refreshToken;

	@ApiModelProperty(value = "Id of modules")
	private List<UUID> modulesId;

	/**
	 * @return the modulesId
	 */
	public List<UUID> getModulesId() {
		return modulesId;
	}

	/**
	 * @param modulesId the modulesId to set
	 */
	public void setModulesId(List<UUID> modulesId) {
		this.modulesId = modulesId;
	}

	/**
	 * @return the refreshToken
	 */
	public String getRefreshToken() {
		return refreshToken;
	}

	/**
	 * @param refreshToken the refreshToken to set
	 */
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
}
