/*
 * AuthController.java
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Control class referring to auth and manage users.
 *
 * @author Manoel Veríssimo dos Santos Neto
 */
@RestController
@Api(tags = "Auth API")
@RequestMapping("${app.api.base}/auth")
@Scope("prototype")
public class AuthController {
    @Autowired
    private AuthService authService;

    /**
     * Authenticates the user in applying a temporary access token.
     *
     * @param authTO
     * @return
     */
    @ApiOperation(value = "Authenticates the user in applying a temporary access token.", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({ @ApiResponse(code = 200, message = "Success", response = CredentialTO.class),
            @ApiResponse(code = 403, message = "Denied", response = MessageResponse.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class)
    })
    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(
            @ApiParam(value = "Auth informations", required = true) @Valid @RequestBody final AuthTO authTO) {
        CredentialTO credentialTO = authService.login(authTO);
        return ResponseEntity.ok(credentialTO);
    }

    /**
     * Generates a new access token through the refresh token entered.
     *
     * @param refreshTO
     * @return
     */
    @ApiOperation(value = "Generates a new access token through the refresh token entered.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = CredentialTO.class),
            @ApiResponse(code = 403, message = "Proibido", response = MessageResponse.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class) })
    @PostMapping(path = "/refresh", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> refresh(@ApiParam(value = "Informações de Autenticação", required = true) @Valid @RequestBody  final RefreshTO refreshTO) {
        CredentialTO credentialTO = authService.refresh(refreshTO);
        return ResponseEntity.ok(credentialTO);
    }
}
