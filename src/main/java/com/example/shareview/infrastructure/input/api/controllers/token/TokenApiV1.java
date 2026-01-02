package com.example.shareview.infrastructure.input.api.controllers.token;

import com.example.shareview.controllers.TokenController;
import com.example.shareview.datasources.TokenDataSource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/token")
@Tag(name = "Token API V1", description = "Versão 1 do controlador referente a token")
@Profile("api")
public class TokenApiV1 {

    private final TokenController tokenController;

    public TokenApiV1(TokenDataSource tokenDataSource) {
        this.tokenController = new TokenController(tokenDataSource);
    }

    @Operation(summary = "Gera token de acesso de cliente",
            description = "Requer autenticação",
            security = @SecurityRequirement(name = "basicAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Token gerado com sucesso",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "401",
                    description = "Credenciais de acesso inválidas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping
    public ResponseEntity<String> generateToken(@AuthenticationPrincipal UserDetails userDetails) {
        log.info("User {} generating token", userDetails.getUsername());
        String token = tokenController.generateToken(String.valueOf(userDetails.getAuthorities().stream().findFirst().get()), userDetails.getUsername());
        log.info("User {} generated token", userDetails.getUsername());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(token);
    }

}
