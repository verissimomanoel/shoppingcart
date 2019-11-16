/*
 * CartRepository.java
 *
 * This is a free software.
 */
package br.com.sample.shoppingcart.api.cart;

import br.com.sample.shoppingcart.api.auth.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository to data access of entity {@link User}
 *
 * @author Manoel Veríssimo dos Santos Neto
 */
@Repository
public interface CartRepository extends MongoRepository<Cart, String> {
    /**
     * Search cart by user id, ordered by item name.
     *
     * @param userId
     * @return
     */
    @Query(value = "{'user.id': ?0}", sort = "{ 'items.item.name': 1 }")
    Optional<Cart> findByUserId(String userId);
}
