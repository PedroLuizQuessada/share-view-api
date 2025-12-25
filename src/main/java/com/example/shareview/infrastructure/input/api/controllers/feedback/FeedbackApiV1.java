package com.example.shareview.infrastructure.input.api.controllers.feedback;

import com.example.shareview.controllers.FeedbackController;
import com.example.shareview.datasources.ClassDataSource;
import com.example.shareview.datasources.FeedbackDataSource;
import com.example.shareview.datasources.TokenDataSource;
import com.example.shareview.datasources.UserDataSource;
import com.example.shareview.dtos.requests.CreateFeedbackRequest;
import com.example.shareview.dtos.responses.FeedbackResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/feedbacks")
@Tag(name = "Feedback API V1", description = "Versão 1 do controlador referente a feedbacks")
@Profile("api")
public class FeedbackApiV1 {

    private final FeedbackController feedbackController;

    public FeedbackApiV1(TokenDataSource tokenDataSource, UserDataSource userDataSource, ClassDataSource classDataSource,
                         FeedbackDataSource feedbackDataSource) {
        this.feedbackController = new FeedbackController(tokenDataSource, userDataSource, classDataSource, feedbackDataSource);
    }

    @Operation(summary = "Cria um feedback",
            description = "Requer autenticação e tipo de usuário 'STUDENT'",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "201",
                    description = "Feedback criado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FeedbackResponse.class))),
            @ApiResponse(responseCode = "400",
                    description = "Valores inválidos para os atributos do feedback a ser criado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401",
                    description = "Credenciais de acesso inválidas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "403",
                    description = "Usuário autenticado não é 'STUDENT'",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404",
                    description = "Classe a receber feedback não encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping
    public ResponseEntity<FeedbackResponse> createFeedback(@Parameter(hidden = true) @RequestHeader(name = "Authorization") String token,
                                                           @RequestBody @Valid CreateFeedbackRequest createFeedbackRequest) {
        log.info("Creating feedback to class {}", createFeedbackRequest.classId());
        FeedbackResponse feedbackResponse = feedbackController.createFeedback(token, createFeedbackRequest);
        log.info("Created feedback to class {}", feedbackResponse.classId());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(feedbackResponse);
    }

}
