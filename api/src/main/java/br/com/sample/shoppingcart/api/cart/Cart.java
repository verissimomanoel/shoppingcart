/*
 * Cart.java
 *
 * This is a free software.
 */
package br.com.sample.shoppingcart.api.cart;

import br.com.sample.shoppingcart.api.auth.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Entity class to represent data of 'Cart'.
 *
 * @author Manoel Veríssimo dos Santos Neto
 */
@Getter
@Setter
@Document(collection="Cart")
public class Cart {
    @ApiModelProperty(value = "Id of cart")
    @Id
    private String id;

    @ApiModelProperty(value = "Amount of value in cart")
    private double amount;

    @NotNull
    @ApiModelProperty(value = "User owner of cart")
    private User user;

    @ApiModelProperty(value = "List of item of cart")
    private List<ItemCart> items;
}
