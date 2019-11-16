/*
 * UserRepository.java
 *
 * This is a free software.
 */
package br.com.sample.shoppingcart.api.auth;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository to data access of entity {@link User}
 *
 * @author Manoel Veríssimo dos Santos Neto
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {
    /**
     * Find user by name and email.
     * @param name
     * @param email
     * @return
     */
    Optional<User> findUserByNameAndEmail(String name, String email);

    /**
     * Find user by email and password.
     *
     * @param email
     * @param password
     * @return
     */
    Optional<User> findUserByEmailAndPassword(String email, String password);

    /**
     * Search user by name (staring with).
     *
     * @param name
     * @return
     */
    @Query(value = "{'name': {$regex : ?0, $options: 'i'}}", sort = "{ name: 1}")
    List<User> findByName(String name);
}
