/*
 * ItemService.java
 *
 * This is a free software.
 */
package br.com.sample.shoppingcart.api.item;

import br.com.sample.shoppingcart.api.exception.BusinessException;

import java.util.List;

/**
 * Interface of business class of {@link Item}.
 *
 * @author Manoel Veríssimo dos Santos Neto
 */
public interface ItemService {
    /**
     * Save data of entity {@link Item}.
     *
     * @param item
     * @return
     * @throws BusinessException
     */
    Item save(Item item) throws BusinessException;

    /**
     * Update data of entity {@link Item}.
     *
     * @param item
     * @return
     * @throws BusinessException
     */
    Item update(Item item) throws BusinessException;

    /**
     * Delete data of entity {@link Item}, by id.
     *
     * @param id
     * @throws BusinessException
     */
    void delete(String id) throws BusinessException;

    /**
     * Search items by name.
     *
     * @param name
     * @return
     * @throws BusinessException
     */
    List<Item> search(String name) throws BusinessException;

    /**
     * List all item.
     *
     * @return
     * @throws BusinessException
     */
    List<Item> listAll() throws BusinessException;
}
