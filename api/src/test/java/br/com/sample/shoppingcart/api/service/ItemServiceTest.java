/*
 * ItemServiceTest.java
 *
 * This is a free software.
 */
package br.com.sample.shoppingcart.api.service;

import br.com.sample.shoppingcart.api.exception.BusinessException;
import br.com.sample.shoppingcart.api.item.Item;
import br.com.sample.shoppingcart.api.item.ItemRepository;
import br.com.sample.shoppingcart.api.item.ItemService;
import br.com.sample.shoppingcart.api.item.impl.ItemServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Implementation test for business class {@link ItemService}.
 *
 * @author Manoel Veríssimo dos Santos Neto
 */
@SpringBootTest
public class ItemServiceTest {
    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService itemService = new ItemServiceImpl();

    /**
     * Init class Test.
     */
    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void whenValidItem_thenItemShouldBeSaved() {
        Item item = getItemMock();
        when(itemRepository.save(item)).thenReturn(item);

        Item result = itemService.save(item);
        assertNotNull(result);
    }

    @Test
    public void whenUpdateWithoutItem_thenItemShouldNotBeUpdated() {
        assertThrows(BusinessException.class, () -> {
            itemService.update(null);
        });
    }

    @Test
    public void whenDeleteValidItem_thenItemShouldBeDeleted() {
        String id = "123";
        doNothing().when(itemRepository).deleteById(id);
        itemService.delete(id);
    }

    @Test
    public void whenDeleteWithoutId_thenItemShouldNotBeDeleted() {
        String id = "123";
        doNothing().when(itemRepository).deleteById(id);
        assertThrows(BusinessException.class, () -> {
            itemService.delete(null);
        });
    }

    @Test
    public void whenSearchItemsWithName_thenItemsShouldBeFound() {
        Item item = getItemMock();
        String name = "Name";
        List<Item> items = new ArrayList<>();
        items.add(item);
        when(itemRepository.findByName(name)).thenReturn(items);

        List<Item> result = itemService.search(name);
        assertNotNull(result);
    }

    @Test
    public void whenSearchItemsWithoutName_thenItemsShouldNotBeFound() {
        when(itemRepository.findByName(null)).thenReturn(null);

        assertThrows(BusinessException.class, () -> {
            itemService.search(null);
        });
    }

    @Test
    public void whenSearchItemsNameDontExists_thenItemsShouldNotBeFound() {
        String name = "Name";
        when(itemRepository.findByName(name)).thenReturn(null);

        assertThrows(BusinessException.class, () -> {
            itemService.search(name);
        });
    }

    @Test
    public void whenListItemsWithName_thenItemsShouldBeFound() {
        Item item = getItemMock();
        String name = "Name";
        List<Item> items = new ArrayList<>();
        items.add(item);
        when(itemRepository.findAll()).thenReturn(items);

        List<Item> result = itemService.listAll();
        assertNotNull(result);
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
