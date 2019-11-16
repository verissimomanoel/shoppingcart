/*
 * CartAddTO.java
 *
 * This is a free software.
 */
package br.com.sample.shoppingcart.api.cart;

import br.com.sample.shoppingcart.api.auth.User;
import br.com.sample.shoppingcart.api.item.Item;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * Transfer object class responsible for cart operations.
 *
 * @author Manoel Veríssimo dos Santos Neto
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
public class CartAddTO {
    @ApiModelProperty(value = "Id of user owner of cart")
    @NotNull
    private String userId;

    @ApiModelProperty(value = "Item to add in cart")
    @NotNull
    private Item item;
}
