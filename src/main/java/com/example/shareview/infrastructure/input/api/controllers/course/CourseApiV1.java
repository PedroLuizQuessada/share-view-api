package com.example.shareview.infrastructure.input.api.controllers.course;

import com.example.shareview.controllers.CourseController;
import com.example.shareview.datasources.ClassDataSource;
import com.example.shareview.datasources.CourseDataSource;
import com.example.shareview.dtos.requests.CreateCourseRequest;
import com.example.shareview.dtos.requests.UpdateCourseRequest;
import com.example.shareview.dtos.responses.CourseResponse;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping(path = "/api/v1/cursos")
@Tag(name = "Course API V1", description = "Versão 1 do controlador referente a cursos")
@Profile("api")
public class CourseApiV1 {

    private final CourseController courseController;

    public CourseApiV1(CourseDataSource courseDataSource, ClassDataSource classDataSource) {
        this.courseController = new CourseController(courseDataSource, classDataSource);
    }

    @Operation(summary = "Cria um curso",
            description = "Requer autenticação e tipo de usuário 'ADMIN'",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "201",
                    description = "Curso criado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CourseResponse.class))),
            @ApiResponse(responseCode = "400",
                    description = "Valores inválidos para os atributos do curso a ser criado",
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
    public ResponseEntity<CourseResponse> createCourse(@RequestBody @Valid CreateCourseRequest createCourseRequest) {
        log.info("Creating course: {}", createCourseRequest.name());
        CourseResponse courseResponse = courseController.createCourse(createCourseRequest);
        log.info("Created course: {}", courseResponse.name());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(courseResponse);
    }

    @Operation(summary = "Apaga um curso",
            description = "Requer autenticação e tipo de usuário 'ADMIN'",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "204",
                    description = "Usuário apagado com sucesso"),
            @ApiResponse(responseCode = "401",
                    description = "Credenciais de acesso inválidas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "403",
                    description = "Usuário autenticado não é 'ADMIN'",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404",
                    description = "Curso a ser apagado não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable("id") Long courseId) {
        log.info("Deleting course: {}", courseId);
        courseController.deleteCourse(courseId);
        log.info("Deleted course: {}", courseId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @Operation(summary = "Atualiza um curso",
            description = "Requer autenticação e tipo de usuário 'ADMIN'",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "204",
                    description = "Curso atualizado com sucesso"),
            @ApiResponse(responseCode = "400",
                    description = "Valores inválidos para os atributos do curso a ser atualizado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401",
                    description = "Credenciais de acesso inválidas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "403",
                    description = "Usuário autenticado não é 'ADMIN'",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404",
                    description = "Curso a ser atualizado não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCourse(@PathVariable("id") Long courseId, @RequestBody @Valid UpdateCourseRequest updateCourseRequest) {
        log.info("Updating course: {}", courseId);
        courseController.updateCourse(courseId, updateCourseRequest);
        log.info("Updated course: {}", courseId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
