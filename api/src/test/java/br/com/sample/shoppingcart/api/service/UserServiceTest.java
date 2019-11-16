/*
 * UserServiceTest.java
 *
 * This is a free software.
 */
package br.com.sample.shoppingcart.api.service;

import br.com.sample.shoppingcart.api.auth.User;
import br.com.sample.shoppingcart.api.auth.UserRepository;
import br.com.sample.shoppingcart.api.auth.UserService;
import br.com.sample.shoppingcart.api.auth.impl.UserServiceImpl;
import br.com.sample.shoppingcart.api.exception.BusinessException;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Implementation test for business class {@link UserService}.
 *
 * @author Manoel Veríssimo dos Santos Neto
 */
@SpringBootTest
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService = new UserServiceImpl();

    /**
     * Init class Test.
     */
    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void whenValidUser_thenUserShouldBeSaved() {
        User user = getUserMock();
        when(userRepository.findUserByNameAndEmail(user.getName(), user.getEmail()))
                .thenReturn(Optional.empty());
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.save(user);
        assertNotNull(result);
    }

    @Test
    public void whenInvalidUser_thenUserShouldNotBeSaved() {
        User user = getUserMock();
        User userSaved = getUserMock();
        userSaved.setId("321");
        when(userRepository.findUserByNameAndEmail(user.getName(), user.getEmail()))
                .thenReturn(Optional.of(userSaved));
        when(userRepository.save(user)).thenReturn(user);

        assertThrows(BusinessException.class, () -> {
            User result = userService.save(user);
        });
    }

    @Test
    public void whenUpdateWithoutUser_thenUserShouldNotBeUpdated() {
        assertThrows(BusinessException.class, () -> {
            userService.update(null);
        });
    }

    @Test
    public void whenDeleteValidUser_thenUserShouldBeDeleted() {
        String id = "123";
        doNothing().when(userRepository).deleteById(id);
        userService.delete(id);
    }

    @Test
    public void whenDeleteWithoutId_thenUserShouldNotBeDeleted() {
        String id = "123";
        doNothing().when(userRepository).deleteById(id);
        assertThrows(BusinessException.class, () -> {
            userService.delete(null);
        });
    }

    @Test
    public void whenSearchUsersWithName_thenUsersShouldBeFound() {
        User user = getUserMock();
        String name = "Name";
        List<User> users = new ArrayList<>();
        users.add(user);
        when(userRepository.findByName(name)).thenReturn(users);

        List<User> result = userService.search(name);
        assertNotNull(result);
    }

    @Test
    public void whenSearchUsersWithoutName_thenUsersShouldNotBeFound() {
        when(userRepository.findByName(null)).thenReturn(null);

        assertThrows(BusinessException.class, () -> {
            userService.search(null);
        });
    }

    @Test
    public void whenSearchUsersNameDontExists_thenUsersShouldNotBeFound() {
        String name = "Name";
        when(userRepository.findByName(name)).thenReturn(null);

        assertThrows(BusinessException.class, () -> {
            userService.search(name);
        });
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
