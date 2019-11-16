/*
 * AuthControllerTest.java
 *
 * This is a free software.
 */
package br.com.sample.shoppingcart.api.controller;

import br.com.sample.shoppingcart.api.auth.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Implementation test for controller class {@link AuthController}.
 *
 * @author Manoel Veríssimo dos Santos Neto
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class AuthControllerTest {
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext wac;

    @MockBean
    private AuthService service;

    @BeforeEach
    public void setup() {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.wac)
                .apply(springSecurity()).build();
    }

    @Test
    public void whenLogin_thenReturnCredentials()
            throws Exception {

        AuthTO authTO = new AuthTO();
        authTO.setLogin("admin");
        authTO.setPassword("admin");

        CredentialTO credentialTO = new CredentialTO();
        credentialTO.setLogin("admin");

        when(service.login(authTO)).thenReturn(credentialTO);

        mvc.perform(post("/api/v1/auth/login").with(csrf())
                .content(asJsonString(authTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
