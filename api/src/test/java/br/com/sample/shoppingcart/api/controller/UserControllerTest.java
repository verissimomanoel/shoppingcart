/*
 * UserControllerTest.java
 *
 * This is a free software.
 */
package br.com.sample.shoppingcart.api.controller;

import br.com.sample.shoppingcart.api.auth.User;
import br.com.sample.shoppingcart.api.auth.UserController;
import br.com.sample.shoppingcart.api.auth.UserRepository;
import br.com.sample.shoppingcart.api.security.ClaimResolve;
import br.com.sample.shoppingcart.api.security.KeyToken;
import br.com.sample.shoppingcart.api.security.Token;
import br.com.sample.shoppingcart.api.security.TokenBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Implementation test for controller class {@link UserController}.
 *
 * @author Manoel Veríssimo dos Santos Neto
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class UserControllerTest {
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private KeyToken keyToken;

    @MockBean
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.wac)
                .apply(springSecurity()).build();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void whenCreate_thenReturnUser()
            throws Exception {

        User user = getUserMock();
        String token = getToken(user);

        when(userRepository.save(user)).thenReturn(user);

        mvc.perform(post("/api/v1/user").with(csrf()).header("Authorization", token)
                .content(asJsonString(user))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenCreateDuplicatedUser_thenUserShouldNotBeSaved() throws Exception {
        User user = getUserMock();
        User userSearch = getUserMock();
        userSearch.setId("a93828");
        TokenBuilder builder = new TokenBuilder(keyToken);
        builder.addName(user.getName());
        builder.addLogin(user.getEmail());
        builder.addParam(ClaimResolve.PARAM_USER, user.getId());
        when(userRepository.findUserByNameAndEmail(user.getName(), user.getEmail()))
                .thenReturn(Optional.of(userSearch));

        Token accessToken = builder.buildAccess();

        String token = "Bearer " + accessToken.getValue();

        mvc.perform(post("/api/v1/user").with(csrf()).header("Authorization", token)
                .content(asJsonString(user))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenUpdate_thenReturnUser()
            throws Exception {

        User user = getUserMock();
        String token = getToken(user);

        when(userRepository.save(user)).thenReturn(user);

        mvc.perform(put("/api/v1/user").with(csrf()).header("Authorization", token)
                .content(asJsonString(user))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenDelete_thenUserShouldBeDeleted()
            throws Exception {

        User user = getUserMock();
        String token = getToken(user);

        doNothing().when(userRepository).deleteById(user.getId());

        mvc.perform(delete("/api/v1/user/" + user.getId()).with(csrf()).header("Authorization", token)
                .content(asJsonString(user))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private String getToken(User user) {
        TokenBuilder builder = new TokenBuilder(keyToken);
        builder.addName(user.getName());
        builder.addLogin(user.getEmail());
        builder.addParam(ClaimResolve.PARAM_USER, user.getId());

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
     * Return an instance of user.
     *
     * @return
     */
    private User getUserMock() {
        User user = new User();
        user.setId("123");
        user.setName("Name");
        user.setEmail("email@gmail.com");
        user.setPassword("password");

        return user;
    }
}
