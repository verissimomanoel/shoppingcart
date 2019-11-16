/*
 * UserService.java
 *
 * This is a free software.
 */
package br.com.sample.shoppingcart.api.auth;

import br.com.sample.shoppingcart.api.exception.BusinessException;

import java.util.List;

/**
 * Interface of business class of {@link User}.
 *
 * @author Manoel Veríssimo dos Santos Neto
 */
public interface UserService {
    /**
     * Save data of entity {@link User}.
     *
     * @param user
     * @return
     * @throws BusinessException
     */
    User save(User user) throws BusinessException;

    /**
     * Update data of entity {@link User}.
     *
     * @param user
     * @return
     * @throws BusinessException
     */
    User update(User user) throws BusinessException;

    /**
     * Delete data of entity {@link User}, by id.
     *
     * @param id
     * @throws BusinessException
     */
    void delete(String id) throws BusinessException;

    /**
     * Search users by name.
     *
     * @param name
     * @return
     * @throws BusinessException
     */
    List<User> search(String name) throws BusinessException;
}
