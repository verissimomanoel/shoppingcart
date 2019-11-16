/*
 * CartService.java
 *
 * This is a free software.
 */
package br.com.sample.shoppingcart.api.cart;

import br.com.sample.shoppingcart.api.exception.BusinessException;

import java.util.List;

/**
 * Interface of business class of {@link Cart}.
 *
 * @author Manoel Veríssimo dos Santos Neto
 */
public interface CartService {
    /**
     * Search cart by user id.
     *
     * @param userId
     * @return
     */
    Cart findByUserId(String userId);

    /**
     * Save data of entity {@link Cart}.
     *
     * @param cart
     * @return
     * @throws BusinessException
     */
    Cart save(Cart cart) throws BusinessException;

    /**
     * Add items on user cart.
     *
     * @param cartAddTO
     * @return
     * @throws BusinessException
     */
    Cart addItem(CartAddTO cartAddTO) throws BusinessException;

    /**
     * Add items on user cart.
     *
     * @param userId
     * @param itemId
     * @return
     * @throws BusinessException
     */
    Cart removeItem(String userId, String itemId) throws BusinessException;

    /**
     * Close the cart.
     *
     * @param cartId
     * @return
     */
    Cart close(String cartId);

    /**
     * List all carts.
     *
     * @return
     */
    List<Cart> listAll();
}
