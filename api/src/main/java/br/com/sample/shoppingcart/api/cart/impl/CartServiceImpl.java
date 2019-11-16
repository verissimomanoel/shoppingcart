/*
 * CartServiceImpl.java
 *
 * This is a free software.
 */
package br.com.sample.shoppingcart.api.cart.impl;

import br.com.sample.shoppingcart.api.cart.*;
import br.com.sample.shoppingcart.api.exception.BusinessException;
import br.com.sample.shoppingcart.api.exception.ShoppingCartMessageCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Business class of {@link Cart}.
 *
 * @author Manoel Veríssimo dos Santos Neto
 */
@Service
@Scope("prototype")
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;

    /**
     * @see CartService#findByUserId(String)
     */
    @Override
    public Cart findByUserId(String userId) {
        if (StringUtils.isEmpty(userId)) {
            throw new BusinessException(ShoppingCartMessageCode.ERROR_REQUIRED_FIELDS, "UserId");
        }

        Optional<Cart> cartOptional = cartRepository.findByUserId(userId);
        cartOptional.orElseThrow(() -> new BusinessException(ShoppingCartMessageCode.ERROR_NO_CART_FOUND));

        return cartOptional.get();
    }

    /**
     * @see CartService#save(Cart)
     */
    @Override
    public Cart save(Cart cart) throws BusinessException {
        if (cart == null) {
            throw new BusinessException(ShoppingCartMessageCode.ERROR_REQUIRED_FIELDS, "UserId");
        }

        Optional<Cart> cartOptional = cartRepository.findByUserId(cart.getUser().getId());
        if (cartOptional.isPresent()) {
            throw new BusinessException(ShoppingCartMessageCode.ERROR_CART_ALREADY_EXISTS);
        }
        return cartRepository.save(cart);
    }

    /**
     * @see CartService#addItem(CartAddTO)
     */
    @Override
    public Cart addItem(CartAddTO cartAddTO) throws BusinessException {
        validateItem(cartAddTO);

        Cart cart = this.findByUserId(cartAddTO.getUserId());
        ItemCart itemCart = null;

        if (cart.getItems() == null) {
            cart.setItems(new ArrayList<>());
        } else {
            itemCart = cart.getItems().stream()
                    .filter(x -> cartAddTO.getItem().getId().equals(x.getItem().getId()))
                    .findAny()
                    .orElse(null);
        }

        if (itemCart != null) {
            itemCart.setQuantity(itemCart.getQuantity() + 1);
        } else {
            itemCart = new ItemCart();
            itemCart.setItem(cartAddTO.getItem());
            itemCart.setQuantity(1);
            cart.getItems().add(itemCart);
        }

        cart.setAmount(cart.getAmount() + cartAddTO.getItem().getValue());
        cart.getItems().sort(Comparator.comparing(c -> c.getItem().getName()));

        return cartRepository.save(cart);
    }

    /**
     * @see CartService#removeItem(String, String)
     */
    @Override
    public Cart removeItem(String userId, String itemId) throws BusinessException {
        if (StringUtils.isEmpty(itemId)) {
            throw new BusinessException(ShoppingCartMessageCode.ERROR_REQUIRED_FIELDS, "ItemId");
        }
        Cart cart = this.findByUserId(userId);

        ItemCart itemCart = cart.getItems().stream()
                .filter(x -> itemId.equals(x.getItem().getId()))
                .findAny()
                .orElse(null);

        if (itemCart == null) {
            throw new BusinessException(ShoppingCartMessageCode.ERROR_ITEM_DONT_EXISTS_ON_CART);
        }

        itemCart.setQuantity(itemCart.getQuantity() - 1);
        cart.setAmount(cart.getAmount() - itemCart.getItem().getValue());

        if (itemCart.getQuantity() <= 0) {
            cart.getItems().remove(itemCart);
        }

        if (cart.getItems().isEmpty()) {
            cart.setAmount(BigDecimal.ZERO.doubleValue());
        }

        cart = cartRepository.save(cart);
        cart.getItems().sort(Comparator.comparing(c -> c.getItem().getName()));

        return cart;
    }

    /**
     * @see CartService#close(String)
     */
    @Override
    public Cart close(String cartId) {
        if (StringUtils.isEmpty(cartId)) {
            throw new BusinessException(ShoppingCartMessageCode.ERROR_REQUIRED_FIELDS, "CartId");
        }

        Optional<Cart> cartOptional = cartRepository.findById(cartId);
        cartOptional.orElseThrow(() -> new BusinessException(ShoppingCartMessageCode.ERROR_NO_CART_FOUND));

        Cart cart = cartOptional.get();
        cart.getItems().sort((itemCart1, itemCart2) -> {
            if (itemCart1.getItem().getName().compareTo(itemCart2.getItem().getName()) == 0) {
                return itemCart1.getItem().getValue().compareTo(itemCart2.getItem().getValue());
            } else {
                return itemCart1.getItem().getName().compareTo(itemCart2.getItem().getName());
            }
        });

        return cart;
    }

    /**
     * List all cards ordered by amount.
     *
     * @return
     */
    @Override
    public List<Cart> listAll() {
        List<Cart> carts = cartRepository.findAll();
        carts.sort(Comparator.comparing(Cart::getAmount));
        return carts;
    }

    /**
     * Validate data of item, rules:
     * <p>
     * 1 - Id is required
     * 2 - The value of item needs to greater than zero.
     * </p>
     *
     * @param cartAddTO
     */
    private void validateItem(CartAddTO cartAddTO) {
        if (StringUtils.isEmpty(cartAddTO.getItem().getId())) {
            throw new BusinessException(ShoppingCartMessageCode.ERROR_REQUIRED_FIELDS, "ItemId");
        }

        if (cartAddTO.getItem().getValue() <= 0) {
            throw new BusinessException(ShoppingCartMessageCode.ERROR_ITEM_VALUE);
        }
    }
}
