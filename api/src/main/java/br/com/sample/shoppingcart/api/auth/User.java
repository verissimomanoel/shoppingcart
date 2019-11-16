/*
 * User.java
 *
 * This is a free software.
 */
package br.com.sample.shoppingcart.api.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

/**
 * Entity class to represent data of 'User'.
 *
 * @author Manoel Veríssimo dos Santos Neto
 */
@Document("User")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class User {

    @ApiModelProperty(value = "User id")
    @Id
    private String id;

    @NotNull
    @ApiModelProperty(value = "Username")
    @Indexed(name = "name")
    private String name;

    @NotNull
    @ApiModelProperty(value = "Email of user")
    private String email;

    @NotNull
    @ApiModelProperty(value = "Password of user")
    private String password;
}
