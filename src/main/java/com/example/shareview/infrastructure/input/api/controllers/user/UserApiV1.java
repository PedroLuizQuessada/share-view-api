package com.example.shareview.infrastructure.input.api.controllers.user;

import com.example.shareview.controllers.UserController;
import com.example.shareview.datasources.ClassDataSource;
import com.example.shareview.datasources.TokenDataSource;
import com.example.shareview.datasources.UserDataSource;
import com.example.shareview.dtos.requests.CreateStudentUserRequest;
import com.example.shareview.dtos.requests.CreateUserRequest;
import com.example.shareview.dtos.requests.UpdateUserEmailRequest;
import com.example.shareview.dtos.requests.UpdateUserPasswordRequest;
import com.example.shareview.dtos.responses.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/usuarios")
@Tag(name = "User API V1", description = "Versão 1 do controlador referente a usuários")
@Profile("api")
public class UserApiV1 {

    private final UserController userController;

    public UserApiV1(UserDataSource userDataSource, TokenDataSource tokenDataSource, ClassDataSource classDataSource) {
        this.userController = new UserController(userDataSource, tokenDataSource, classDataSource);
    }

    @Operation(summary = "Cria um usuário aluno",
            description = "Endpoint liberado para usuários não autenticados")
    @ApiResponses({
            @ApiResponse(responseCode = "201",
                    description = "Usuário criado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400",
                    description = "Valores inválidos para os atributos do usuário a ser criado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401",
                    description = "Credenciais de acesso inválidas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping("/aluno")
    public ResponseEntity<UserResponse> createStudentUser(@RequestBody @Valid CreateStudentUserRequest createUserRequest) {
        log.info("Creating student user: {}", createUserRequest.email());
        UserResponse userResponse = userController.createStudentUser(createUserRequest);
        log.info("Created student user: {}", userResponse.email());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userResponse);
    }

    @Operation(summary = "Cria um usuário",
            description = "Requer autenticação e tipo de usuário 'ADMIN'",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "201",
                    description = "Usuário criado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400",
                    description = "Valores inválidos para os atributos do usuário a ser criado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401",
                    description = "Credenciais de acesso inválidas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "403",
                    description = "Usuário autenticado não é 'ADMIN'",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid CreateUserRequest createUserRequest) {
        log.info("Creating user: {}", createUserRequest.email());
        UserResponse userResponse = userController.createUser(createUserRequest);
        log.info("Created user: {}", userResponse.email());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userResponse);
    }

    @Operation(summary = "Apaga o seu próprio usuário",
            description = "Requer autenticação",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "204",
                    description = "Usuário apagado com sucesso"),
            @ApiResponse(responseCode = "401",
                    description = "Credenciais de acesso inválidas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@Parameter(hidden = true) @RequestHeader(name = "Authorization") String token,
                                       HttpSession httpSession) {
        log.info("Deleting user");
        userController.deleteUser(token);
        httpSession.invalidate();
        log.info("Deleted user");

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @Operation(summary = "Atualiza o e-mail do seu próprio usuário",
            description = "Requer autenticação",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "204",
                    description = "E-mail do usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "400",
                    description = "Valores inválidos para os atributos do usuário a ser atualizado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401",
                    description = "Credenciais de acesso inválidas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PutMapping("/email")
    public ResponseEntity<Void> updateUserEmail(@Parameter(hidden = true) @RequestHeader(name = "Authorization") String token,
                                               @RequestBody @Valid UpdateUserEmailRequest updateUserEmailRequest) {
        log.info("Updating user e-mail");
        userController.updateUserEmail(token, updateUserEmailRequest);
        log.info("Updated user e-mail");

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @Operation(summary = "Atualiza a senha do seu próprio usuário",
            description = "Requer autenticação",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "204",
                    description = "Senha do usuário atualizada com sucesso"),
            @ApiResponse(responseCode = "400",
                    description = "Valores inválidos para os atributos do usuário a ser atualizado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401",
                    description = "Credenciais de acesso inválidas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PutMapping("/senha")
    public ResponseEntity<Void> updateUserPassword(@Parameter(hidden = true) @RequestHeader(name = "Authorization") String token,
                                               @RequestBody @Valid UpdateUserPasswordRequest updateUserPasswordRequest) {
        log.info("Updating user password");
        userController.updateUserPassword(token, updateUserPasswordRequest);
        log.info("Updated user password");

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
