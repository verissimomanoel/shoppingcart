/*
 * ItemController.java
 *
 * This is a free software.
 */
package br.com.sample.shoppingcart.api.item;

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
 * Control class referring to manage items.
 *
 * @author Manoel Veríssimo dos Santos Neto
 */
@RestController
@Api(tags = "Item API")
@RequestMapping("${app.api.base}/item")
@Scope("prototype")
public class ItemController {
    @Autowired
    private ItemService itemService;

    /**
     * Create a new item on system.
     *
     * @param item
     * @return
     */
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "Create a new item on system", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({ @ApiResponse(code = 200, message = "Success", response = Item.class),
            @ApiResponse(code = 403, message = "Denied", response = MessageResponse.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(
            @ApiParam(value = "Item informations", required = true) @Valid @RequestBody final Item item) {
        Item itemSaved = itemService.save(item);
        return ResponseEntity.ok(itemSaved);
    }

    /**
     * Update data of item.
     *
     * @param item
     * @return
     */
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "Update data of item", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({ @ApiResponse(code = 200, message = "Success", response = Item.class),
            @ApiResponse(code = 403, message = "Denied", response = MessageResponse.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(
            @ApiParam(value = "Item informations", required = true) @Valid @RequestBody final Item item) {
        Item itemSaved = itemService.update(item);
        return ResponseEntity.ok(itemSaved);
    }

    /**
     * Delete data of item.
     *
     * @param id
     * @return
     */
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "Delete data of item by id", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({ @ApiResponse(code = 200, message = "Success", response = Item.class),
            @ApiResponse(code = 403, message = "Denied", response = MessageResponse.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class)
    })
    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(
            @ApiParam(value = "Id of item", required = true) @PathVariable final String id) {
        itemService.delete(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Search item using name reported.
     * 
     * @return
     */
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "Search item using name reported", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({ @ApiResponse(code = 200, message = "Success", response = Item.class),
            @ApiResponse(code = 403, message = "Denied", response = MessageResponse.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class)
    })
    @GetMapping(path = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> search(
            @ApiParam(value = "Name of item") @PathVariable final String name) {
        List<Item> items = itemService.search(name);

        return ResponseEntity.ok(items);
    }

    /**
     * List all items.
     *
     * @return
     */
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "List all items", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({ @ApiResponse(code = 200, message = "Success", response = Item.class),
            @ApiResponse(code = 403, message = "Denied", response = MessageResponse.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class)
    })
    @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> search() {
        List<Item> items = itemService.listAll();

        return ResponseEntity.ok(items);
    }
}
