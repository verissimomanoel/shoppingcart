/*
 * ShoppingCartApplication.java
 *
 * This is a free software.
 */
package br.com.sample.shoppingcart.api;

import br.com.sample.shoppingcart.api.auth.User;
import br.com.sample.shoppingcart.api.auth.UserRepository;
import br.com.sample.shoppingcart.api.util.Util;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Optional;

/**
 * The main class to start the application.
 */
@SpringBootApplication(scanBasePackages = { "br.com.sample.shoppingcart.api" })
public class ShoppingCartApplication {

	/**
	 * Main method to start the application.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(ShoppingCartApplication.class, args);
	}

	/**
	 * Init the {@link UserRepository} to create admin user on the first start application.
	 *
	 * @param userRepository
	 * @return
	 */
	@Bean
	CommandLineRunner init(UserRepository userRepository) {
		return args -> initUsers(userRepository);
	}

	/**
	 * Create a user admin.
	 *
	 * @param userRepository
	 */
	private void initUsers(UserRepository userRepository) {
		Optional<User> userOptional = userRepository.findUserByNameAndEmail("admin", "admincart@gmail.com");
		if (!userOptional.isPresent()) {
			User user = new User();
			user.setName("admin");
			user.setEmail("admincart@gmail.com");
			user.setPassword(Util.encrypt("admin@123"));
			userRepository.save(user);
		}
	}
}
