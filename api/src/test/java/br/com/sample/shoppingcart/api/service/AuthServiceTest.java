/*
 * AuthServiceTest.java
 *
 * This is a free software.
 */
package br.com.sample.shoppingcart.api.service;

import br.com.sample.shoppingcart.api.auth.*;
import br.com.sample.shoppingcart.api.auth.impl.AuthServiceImpl;
import br.com.sample.shoppingcart.api.security.ClaimResolve;
import br.com.sample.shoppingcart.api.security.KeyToken;
import br.com.sample.shoppingcart.api.security.Token;
import br.com.sample.shoppingcart.api.security.TokenBuilder;
import br.com.sample.shoppingcart.api.util.Util;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * Implementation test for business class {@link AuthService}.
 *
 * @author Manoel Veríssimo dos Santos Neto
 */
@SpringBootTest
public class AuthServiceTest {
    @Mock
    private UserRepository userRepository;

    @MockBean
    private KeyToken keyToken;

    @InjectMocks
    private AuthService authService = new AuthServiceImpl();

    /**
     * Init class Test.
     */
    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void whenValidAuth_thenReturnCredentials() {
        AuthTO authTO = new AuthTO();
        authTO.setLogin("admincart@gmail.com");
        authTO.setPassword("admin@123");
        String password = Util.encrypt(authTO.getPassword());
        User user = getUserMock();
        when(keyToken.getSecret()).thenReturn("123".getBytes());
        when(userRepository.findUserByEmailAndPassword(authTO.getLogin(), password))
                .thenReturn(Optional.of(user));

        CredentialTO result = authService.login(authTO);
        assertNotNull(result);
    }

    @Test
    public void whenValidRefreshToken_thenReturnCredentials() {
        RefreshTO refreshTO = new RefreshTO();
        User user = getUserMock();
        when(keyToken.getSecret()).thenReturn("secretAPIShoppingCart".getBytes());
        when(keyToken.getIssuer()).thenReturn("shoppingcart-api");
        refreshTO.setRefreshToken(getRefreshToken(user));
        CredentialTO result = authService.refresh(refreshTO);
        assertNotNull(result);
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
        user.setEmail("admincart@gmail.com");
        user.setPassword("admin@123");

        return user;
    }

    /**
     * Return a valid refresh token.
     * @param user
     * @return
     */
    private String getRefreshToken(User user) {
        TokenBuilder builder = new TokenBuilder(keyToken);
        builder.addName(user.getName());
        builder.addLogin(user.getEmail());
        builder.addParam(ClaimResolve.PARAM_USER, user.getId());

        Token refreshToken = builder.buildRefresh();

        return "Bearer " + refreshToken.getValue();
    }
}
