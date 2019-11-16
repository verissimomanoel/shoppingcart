/*
 * ItemControllerTest.java
 *
 * This is a free software.
 */
package br.com.sample.shoppingcart.api.controller;

import br.com.sample.shoppingcart.api.item.Item;
import br.com.sample.shoppingcart.api.item.ItemController;
import br.com.sample.shoppingcart.api.item.ItemRepository;
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

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Implementation test for controller class {@link ItemController}.
 *
 * @author Manoel Veríssimo dos Santos Neto
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class ItemControllerTest {
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private KeyToken keyToken;

    @MockBean
    private ItemRepository itemRepository;

    @BeforeEach
    public void setup() {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.wac)
                .apply(springSecurity()).build();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void whenCreate_thenReturnItem()
            throws Exception {

        Item item = getItemMock();
        String token = getToken();

        when(itemRepository.save(item)).thenReturn(item);

        mvc.perform(post("/api/v1/item").with(csrf()).header("Authorization", token)
                .content(asJsonString(item))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenUpdate_thenReturnItem()
            throws Exception {

        Item item = getItemMock();
        String token = getToken();

        when(itemRepository.save(item)).thenReturn(item);

        mvc.perform(put("/api/v1/item").with(csrf()).header("Authorization", token)
                .content(asJsonString(item))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenDelete_thenItemShouldBeDeleted()
            throws Exception {

        Item item = getItemMock();
        String token = getToken();

        doNothing().when(itemRepository).deleteById(item.getId());

        mvc.perform(delete("/api/v1/item/" + item.getId()).with(csrf()).header("Authorization", token)
                .content(asJsonString(item))
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

    private static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Return an instance of item.
     *
     * @return
     */
    private Item getItemMock() {
        Item item = new Item();
        item.setId("123");
        item.setName("Name");
        item.setValue(20D);

        return item;
    }
}
