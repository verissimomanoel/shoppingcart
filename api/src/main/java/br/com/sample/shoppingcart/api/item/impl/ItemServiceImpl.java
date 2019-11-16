/*
 * ItemServiceImpl.java
 *
 * This is a free software.
 */
package br.com.sample.shoppingcart.api.item.impl;

import br.com.sample.shoppingcart.api.exception.BusinessException;
import br.com.sample.shoppingcart.api.exception.ShoppingCartMessageCode;
import br.com.sample.shoppingcart.api.item.Item;
import br.com.sample.shoppingcart.api.item.ItemRepository;
import br.com.sample.shoppingcart.api.item.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Comparator;
import java.util.List;

/**
 * Business class of {@link Item}.
 *
 * @author Manoel Veríssimo dos Santos Neto
 */
@Service
@Scope("prototype")
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemRepository itemRepository;

    /**
     * @see ItemService#save(Item)
     */
    @Override
    public Item save(Item item) throws BusinessException {
        return itemRepository.save(item);
    }

    /**
     * @see ItemService#update(Item)
     */
    @Override
    public Item update(Item item) throws BusinessException {
        if (item == null || StringUtils.isEmpty(item.getId())) {
            throw new BusinessException(ShoppingCartMessageCode.ERROR_REQUIRED_FIELDS, "Id");
        }
        return itemRepository.save(item);
    }

    /**
     * @see ItemService#delete(String)
     */
    @Override
    public void delete(String id) throws BusinessException {
        if (StringUtils.isEmpty(id)) {
            throw new BusinessException(ShoppingCartMessageCode.ERROR_REQUIRED_FIELDS, "Id");
        }
        itemRepository.deleteById(id);
    }

    /**
     * @see ItemService#search(String)
     */
    @Override
    public List<Item> search(String name) throws BusinessException {
        if (StringUtils.isEmpty(name)) {
            throw new BusinessException(ShoppingCartMessageCode.ERROR_FILTER_REQUIRED);
        }

        List<Item> items = itemRepository.findByName(name);
        if (items == null || items.isEmpty()) {
            throw new BusinessException(ShoppingCartMessageCode.ERROR_NO_FOUND);
        }
        items.sort(Comparator.comparing(c -> c.getName()));

        return items;
    }

    /**
     * @see ItemService#listAll()
     */
    @Override
    public List<Item> listAll() throws BusinessException {
        List<Item> items = itemRepository.findAll();
        items.sort(Comparator.comparing(c -> c.getName()));
        return items;
    }
}
