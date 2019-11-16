/*
 * CartController.java
 *
 * This is a free software.
 */
package br.com.sample.shoppingcart.api.cart;

import br.com.sample.shoppingcart.api.exception.MessageResponse;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Comparator;

/**
 * Control class referring to manage shopping cart.
 *
 * @author Manoel Veríssimo dos Santos Neto
 */
@RestController
@Api(tags = "Cart API")
@RequestMapping("${app.api.base}/cart")
@Scope("prototype")
public class CartController {
    @Autowired
    private CartService cartService;

    /**
     * Create a new cart to user.
     *
     * @param cart
     * @return
     */
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "Create a new cart to user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({ @ApiResponse(code = 200, message = "Success", response = Cart.class),
            @ApiResponse(code = 403, message = "Denied", response = MessageResponse.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(
            @ApiParam(value = "Cart informations", required = true) @Valid @RequestBody final Cart cart) {
        Cart cartSaved = cartService.save(cart);
        return ResponseEntity.ok(cartSaved);
    }

    /**
     * Add items on cart.
     *
     * @param cartAddTO
     * @return
     */
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "Update data of item", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({ @ApiResponse(code = 200, message = "Success", response = Cart.class),
            @ApiResponse(code = 403, message = "Denied", response = MessageResponse.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class)
    })
    @PutMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addItem(
            @ApiParam(value = "Card data with user and item to add", required = true) @Valid @RequestBody final CartAddTO cartAddTO) {
        Cart cartSaved = cartService.addItem(cartAddTO);
        return ResponseEntity.ok(cartSaved);
    }

    /**
     * Get cart by user id reported.
     *
     * @return
     */
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "Get cart by user id reported", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({ @ApiResponse(code = 200, message = "Success", response = Cart.class),
            @ApiResponse(code = 403, message = "Denied", response = MessageResponse.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class)
    })
    @GetMapping(path = "/userId/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> search(
            @ApiParam(value = "Id of user") @PathVariable final String userId) {
        Cart cart = cartService.findByUserId(userId);
        if (cart != null && cart.getItems() != null) {
            cart.getItems().sort(Comparator.comparing(c -> c.getItem().getName()));
        }

        return ResponseEntity.ok(cart);
    }

    /**
     * List all cards, ordered by amount.
     *
     * @return
     */
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "List all cards, ordered by amount", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({ @ApiResponse(code = 200, message = "Success", response = Cart.class),
            @ApiResponse(code = 403, message = "Denied", response = MessageResponse.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class)
    })
    @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> listAll() {
        return ResponseEntity.ok(cartService.listAll());
    }

    /**
     * Delete an item in cart.
     *
     * @param userId
     * @param itemId
     * @return
     */
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "Delete data of item by id", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({ @ApiResponse(code = 200, message = "Success", response = Cart.class),
            @ApiResponse(code = 403, message = "Denied", response = MessageResponse.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class)
    })
    @DeleteMapping(path = "/remove/{userId}/{itemId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> removeItem(
            @ApiParam(value = "Id of user", required = true) @PathVariable final String userId,
            @ApiParam(value = "Id of item", required = true) @PathVariable final String itemId) {
        Cart cart = cartService.removeItem(userId, itemId);
        return ResponseEntity.ok(cart);
    }

    /**
     * Checkout the cart.
     *
     * @return
     */
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "List all cards, ordered by amount", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({ @ApiResponse(code = 200, message = "Success", response = Cart.class),
            @ApiResponse(code = 403, message = "Denied", response = MessageResponse.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class)
    })
    @GetMapping(path = "/checkout/{cartId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> checkout(@ApiParam(value = "Id of cart", required = true) @PathVariable final String cartId) {
        return ResponseEntity.ok(cartService.close(cartId));
    }
}
