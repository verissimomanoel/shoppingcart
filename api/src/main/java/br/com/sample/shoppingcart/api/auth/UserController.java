/*
 * UserController.java
 *
 * This is a free software.
 */
package br.com.sample.shoppingcart.api.auth;

import br.com.sample.shoppingcart.api.exception.MessageResponse;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Control class referring to auth and manage users.
 *
 * @author Manoel Veríssimo dos Santos Neto
 */
@RestController
@Api(tags = "User API")
@RequestMapping("${app.api.base}/user")
@Scope("prototype")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * Create a new user on system.
     *
     * @param user
     * @return
     */
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "Create a new user on system", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({ @ApiResponse(code = 200, message = "Success", response = User.class),
            @ApiResponse(code = 403, message = "Denied", response = MessageResponse.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(
            @ApiParam(value = "User informations", required = true) @Valid @RequestBody final User user) {
        User userSaved = userService.save(user);
        return ResponseEntity.ok(userSaved);
    }

    /**
     * Update data of user.
     *
     * @param user
     * @return
     */
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "Update data of user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({ @ApiResponse(code = 200, message = "Success", response = User.class),
            @ApiResponse(code = 403, message = "Denied", response = MessageResponse.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(
            @ApiParam(value = "User informations", required = true) @Valid @RequestBody final User user) {
        User userSaved = userService.update(user);
        return ResponseEntity.ok(userSaved);
    }

    /**
     * Delete data of user.
     *
     * @param id
     * @return
     */
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "Delete data of user by id", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({ @ApiResponse(code = 200, message = "Success", response = User.class),
            @ApiResponse(code = 403, message = "Denied", response = MessageResponse.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class)
    })
    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(
            @ApiParam(value = "Id of user", required = true) @PathVariable final String id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Search user using name reported.
     * @return
     */
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "Search user using name reported", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({ @ApiResponse(code = 200, message = "Success", response = User.class),
            @ApiResponse(code = 403, message = "Denied", response = MessageResponse.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class)
    })
    @GetMapping(path = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> search(
            @ApiParam(value = "Name of user") @PathVariable final String name) {
        List<User> users = userService.search(name);

        return ResponseEntity.ok(users);
    }
}
