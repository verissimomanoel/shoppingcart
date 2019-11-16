/*
 * CartController.java
 *
 * This is a free software.
 */
package br.com.sample.shoppingcart.api.controller;

import br.com.sample.shoppingcart.api.auth.User;
import br.com.sample.shoppingcart.api.cart.*;
import br.com.sample.shoppingcart.api.item.Item;
import br.com.sample.shoppingcart.api.security.ClaimResolve;
import br.com.sample.shoppingcart.api.security.KeyToken;
import br.com.sample.shoppingcart.api.security.Token;
import br.com.sample.shoppingcart.api.security.TokenBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Implementation test for controller class {@link CartControllerTest}.
 *
 * @author Manoel Veríssimo dos Santos Neto
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class CartControllerTest {
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private KeyToken keyToken;

    @MockBean
    private CartService cartService;

    @MockBean
    private CartRepository cartRepository;

    @BeforeEach
    public void setup() {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.wac)
                .apply(springSecurity()).build();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void whenCreate_thenReturnCart()
            throws Exception {

        Cart cart = getCartMock();
        String token = getToken();

        when(cartRepository.save(cart)).thenReturn(cart);

        mvc.perform(post("/api/v1/cart").with(csrf()).header("Authorization", token)
                .content(asJsonString(cart))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenAddItem_thenReturnCartWithItem()
            throws Exception {

        CartAddTO cartAddTO = getCartAddTOMock();
        String token = getToken();

        mvc.perform(put("/api/v1/cart/add").with(csrf()).header("Authorization", token)
                .content(asJsonString(cartAddTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenRemoveItem_thenReturnCartWithoutItem()
            throws Exception {

        String token = getToken();
        Cart cart = getCartMock();
        String userId = cart.getUser().getId();
        String itemId = cart.getItems().get(0).getItem().getId();

        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(cart));

        mvc.perform(delete("/api/v1/cart/remove/" + itemId + "/" + userId)
                .with(csrf()).header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenCheckout_thenReturnCart()
            throws Exception {

        String token = getToken();
        Cart cart = getCartMock();
        String cartId = cart.getId();

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));

        mvc.perform(get("/api/v1/cart/checkout/" + cartId)
                .with(csrf()).header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenSearchCartByUserId_thenReturnCart()
            throws Exception {

        String token = getToken();
        Cart cart = getCartMock();
        String userId = cart.getUser().getId();
        when(cartService.findByUserId(userId)).thenReturn(cart);
        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(cart));

        mvc.perform(get("/api/v1/cart/userId/5dcdea98c642f7561234e654").with(csrf()).header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenGetAllCarts_thenReturnCarts()
            throws Exception {

        String token = getToken();

        mvc.perform(get("/api/v1/cart/all").with(csrf()).header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private String getToken() {
        TokenBuilder builder = new TokenBuilder(keyToken);
        builder.addName("Name");
        builder.addLogin("email@gmail.com");
        builder.addParam(ClaimResolve.PARAM_USER, "123");

        Token accessToken = builder.buildAccess();

        return "Bearer " + accessToken.getValue();
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

    private static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
