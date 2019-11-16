/*
 * CartServiceTest.java
 *
 * This is a free software.
 */
package br.com.sample.shoppingcart.api.service;

import br.com.sample.shoppingcart.api.auth.User;
import br.com.sample.shoppingcart.api.cart.*;
import br.com.sample.shoppingcart.api.cart.impl.CartServiceImpl;
import br.com.sample.shoppingcart.api.exception.BusinessException;
import br.com.sample.shoppingcart.api.item.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

/**
 * Implementation test for business class {@link CartService}.
 *
 * @author Manoel Veríssimo dos Santos Neto
 */
@SpringBootTest
public class CartServiceTest {
    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private CartService cartService = new CartServiceImpl();

    /**
     * Init class Test.
     */
    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void whenFindByUserId_thenCartShouldBeFound() {
        Cart cart = getCartMock();
        when(cartRepository.findByUserId(cart.getUser().getId())).thenReturn(Optional.of(cart));

        Cart result = cartService.findByUserId(cart.getUser().getId());
        assertNotNull(result);
    }

    @Test
    public void whenFindByUserIdWithoutUserId_thenCartShouldNotBeFound() {
        assertThrows(BusinessException.class, () -> {
            cartService.findByUserId(null);
        });
    }

    @Test
    public void whenValidCart_thenCartShouldBeSaved() {
        Cart cart = getCartMock();
        when(cartRepository.save(cart)).thenReturn(cart);

        Cart result = cartService.save(cart);
        assertNotNull(result);
    }

    @Test
    public void whenCartFound_thenCartShouldNotBeFound() {
        Cart cart = getCartMock();
        when(cartRepository.findByUserId(cart.getUser().getId())).thenReturn(Optional.of(cart));

        assertThrows(BusinessException.class, () -> {
            cartService.save(cart);
        });
    }

    @Test
    public void whenNullCart_thenCartShouldNotBeFound() {
        assertThrows(BusinessException.class, () -> {
            cartService.save(null);
        });
    }

    @Test
    public void whenSearchCart_thenCartShouldBeFound() {
        Cart cart = getCartMock();
        when(cartRepository.findByUserId(cart.getUser().getId())).thenReturn(Optional.of(cart));

        Cart result = cartService.findByUserId(cart.getUser().getId());
        assertNotNull(result);
    }

    @Test
    public void whenSearchWithoutUserIdCart_thenCartShouldNotBeFound() {
        assertThrows(BusinessException.class, () -> {
            cartService.findByUserId(null);
        });
    }

    @Test
    public void whenSearchInvalidCart_thenCartShouldNotBeFound() {
        Cart cart = getCartMock();
        when(cartRepository.findByUserId(cart.getUser().getId())).thenReturn(Optional.empty());
        assertThrows(BusinessException.class, () -> {
            cartService.findByUserId(cart.getUser().getId());
        });
    }

    @Test
    public void whenAddItem_thenItemShouldBeAdded() {
        Cart cart = getCartMock();
        CartAddTO cartAddTO = getCartAddTOMock();
        when(cartRepository.findByUserId(cart.getUser().getId())).thenReturn(Optional.of(cart));
        when(cartRepository.save(cart)).thenReturn(cart);

        Cart result = cartService.addItem(cartAddTO);
        assertNotNull(result);
    }

    @Test
    public void whenAddItemInEmptyCart_thenItemShouldBeAdded() {
        Cart cart = getCartMock();
        cart.setItems(null);
        CartAddTO cartAddTO = getCartAddTOMock();
        when(cartRepository.findByUserId(cart.getUser().getId())).thenReturn(Optional.of(cart));
        when(cartRepository.save(cart)).thenReturn(cart);

        Cart result = cartService.addItem(cartAddTO);
        assertNotNull(result);
    }

    @Test
    public void whenAddItemWithoutId_thenItemShouldNotBeAdded() {
        Cart cart = getCartMock();
        cart.getItems().get(0).getItem().setId(null);
        CartAddTO cartAddTO = getCartAddTOMock();

        assertThrows(BusinessException.class, () -> {
            cartService.addItem(cartAddTO);
        });
    }

    @Test
    public void whenAddItemWithZeroValue_thenItemShouldNotBeAdded() {
        Cart cart = getCartMock();
        cart.getItems().get(0).getItem().setValue(0D);
        CartAddTO cartAddTO = getCartAddTOMock();

        assertThrows(BusinessException.class, () -> {
            cartService.addItem(cartAddTO);
        });
    }

    @Test
    public void whenRemoveItem_thenItemShouldBeAdded() {
        Cart cart = getCartMock();
        when(cartRepository.findByUserId(cart.getUser().getId())).thenReturn(Optional.of(cart));
        when(cartRepository.save(cart)).thenReturn(cart);

        Cart result = cartService.removeItem(cart.getUser().getId(), "123");
        assertNotNull(result);
    }

    @Test
    public void whenRemoveItemWithoutId_thenItemShouldNoBeAdded() {
        Cart cart = getCartMock();

        assertThrows(BusinessException.class, () -> {
            cartService.removeItem(cart.getUser().getId(), null);
        });
    }

    @Test
    public void whenCloseCart_thenCartShouldBeClosed() {
        Cart cart = getCartMock();
        String cartId = "123";
        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));

        Cart result = cartService.close(cartId);
        assertNotNull(result);
    }

    @Test
    public void whenCloseCartWithoutCartId_thenCartShouldNotBeClosed() {
        assertThrows(BusinessException.class, () -> {
            cartService.close(null);
        });
    }

    @Test
    public void whenCloseNotFoundCart_thenCartShouldNotBeClosed() {
        String cartId = "123";
        when(cartRepository.findById(cartId)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> {
            Cart result = cartService.close(cartId);
        });
    }

    /**
     * Return an instance of cart.
     *
     * @return
     */
    private Cart getCartMock() {
        Cart cart = new Cart();
        cart.setId("123");
        cart.setAmount(10D);

        ItemCart itemCart = new ItemCart();
        Item item1 = new Item();
        item1.setId("123");
        item1.setName("Item 1");
        item1.setValue(10D);
        itemCart.setItem(item1);
        itemCart.setQuantity(1);
        List<ItemCart> items = new ArrayList<>();
        items.add(itemCart);
        cart.setItems(items);

        User user = new User();
        user.setId("123");
        user.setName("Nam");
        cart.setUser(user);

        return cart;
    }

    /**
     * Return an instance of CartAddTO.
     *
     * @return
     */
    private CartAddTO getCartAddTOMock() {
        CartAddTO cartAddTO = new CartAddTO();
        cartAddTO.setUserId("123");
        Item item1 = new Item();
        item1.setId("123");
        item1.setName("Item 1");
        item1.setValue(10D);
        cartAddTO.setItem(item1);

        return cartAddTO;
    }
}
