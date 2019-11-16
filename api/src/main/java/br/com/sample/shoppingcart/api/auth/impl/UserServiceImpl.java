/*
 * UserServiceImpl.java
 *
 * This is a free software.
 */
package br.com.sample.shoppingcart.api.auth.impl;

import br.com.sample.shoppingcart.api.auth.User;
import br.com.sample.shoppingcart.api.auth.UserRepository;
import br.com.sample.shoppingcart.api.auth.UserService;
import br.com.sample.shoppingcart.api.exception.BusinessException;
import br.com.sample.shoppingcart.api.exception.ShoppingCartMessageCode;
import br.com.sample.shoppingcart.api.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

/**
 * Business class of {@link User}.
 *
 * @author Manoel Veríssimo dos Santos Neto
 */
@Service
@Scope("prototype")
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    /**
     * @see UserService#save(User)
     */
    @Override
    public User save(User user) throws BusinessException {
        checkDuplicity(user);
        user.setPassword(Util.encrypt(user.getPassword()));

        return userRepository.save(user);
    }

    /**
     * Verify if user is duplicated.
     *
     * @param user
     */
    private void checkDuplicity(User user) {
        Optional<User> userOptional = userRepository.findUserByNameAndEmail(user.getName(), user.getEmail());
        if (userOptional.isPresent() && (user.getId() == null || !user.getId().equals(userOptional.get().getId()))) {
            throw new BusinessException(ShoppingCartMessageCode.ERROR_USER_ALREADY_EXISTS);
        }
    }

    /**
     * @see UserService#update(User)
     */
    @Override
    public User update(User user) throws BusinessException {
        if (user == null || StringUtils.isEmpty(user.getId())) {
            throw new BusinessException(ShoppingCartMessageCode.ERROR_REQUIRED_FIELDS, "Id");
        }
        return this.save(user);
    }

    /**
     * @see UserService#delete(String)
     */
    @Override
    public void delete(String id) throws BusinessException {
        if (StringUtils.isEmpty(id)) {
            throw new BusinessException(ShoppingCartMessageCode.ERROR_REQUIRED_FIELDS, "Id");
        }
        userRepository.deleteById(id);
    }

    /**
     * @see UserService#search(String)
     */
    @Override
    public List<User> search(String name) throws BusinessException {
        if (StringUtils.isEmpty(name)) {
            throw new BusinessException(ShoppingCartMessageCode.ERROR_FILTER_REQUIRED);
        }

        List<User> users = userRepository.findByName(name);
        if (users == null || users.isEmpty()) {
            throw new BusinessException(ShoppingCartMessageCode.ERROR_NO_FOUND);
        }
        users.forEach(f -> f.setPassword(null));

        return users;
    }
}
