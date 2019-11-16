/*
 * ItemCart.java
 *
 * This is a free software.
 */
package br.com.sample.shoppingcart.api.cart;

import br.com.sample.shoppingcart.api.item.Item;
import lombok.Getter;
import lombok.Setter;

/**
 * Entity to group items in cart.
 *
 * @author Manoel Veríssimo dos Santos Neto
 */
@Getter
@Setter
public class ItemCart {
    private Item item;

    private Integer quantity;
}
