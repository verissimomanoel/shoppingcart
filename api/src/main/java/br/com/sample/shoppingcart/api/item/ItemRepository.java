/*
 * ItemRepository.java
 *
 * This is a free software.
 */
package br.com.sample.shoppingcart.api.item;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository to data access of entity {@link Item}
 *
 * @author Manoel Veríssimo dos Santos Neto
 */
@Repository
public interface ItemRepository extends MongoRepository<Item, String> {
    /**
     * Search item by name (staring with).
     *
     * @param name
     * @return
     */
    @Query(value = "{'name': {$regex : ?0, $options: 'i'}}", sort = "{ name: 1}")
    List<Item> findByName(String name);
}
