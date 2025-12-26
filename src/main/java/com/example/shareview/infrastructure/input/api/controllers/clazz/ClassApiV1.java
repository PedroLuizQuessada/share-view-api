package com.example.shareview.infrastructure.input.api.controllers.clazz;

import com.example.shareview.controllers.ClassController;
import com.example.shareview.datasources.ClassDataSource;
import com.example.shareview.datasources.CourseDataSource;
import com.example.shareview.datasources.UserDataSource;
import dtos.requests.CreateClassRequest;
import dtos.responses.ClassResponse;
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
@RequestMapping(path = "/api/v1/turmas")
@Tag(name = "Class API V1", description = "Versão 1 do controlador referente a turmas")
@Profile("api")
public class ClassApiV1 {

    private final ClassController classController;

    public ClassApiV1(ClassDataSource classDataSource, UserDataSource userDataSource, CourseDataSource courseDataSource) {
        this.classController = new ClassController(classDataSource, userDataSource, courseDataSource);
    }


    @Operation(summary = "Cria uma turma",
            description = "Requer autenticação e tipo de usuário 'ADMIN'",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "201",
                    description = "Turma criada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClassResponse.class))),
            @ApiResponse(responseCode = "400",
                    description = "Valores inválidos para os atributos da turma a ser criada",
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
                    description = "Curso não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping
    public ResponseEntity<ClassResponse> createClass(@RequestBody @Valid CreateClassRequest createClassRequest) {
        log.info("Creating class of course: {}", createClassRequest.courseId());
        ClassResponse courseResponse = classController.createClass(createClassRequest);
        log.info("Created class of course: {}", createClassRequest.courseId());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(courseResponse);
    }

    @Operation(summary = "Apaga uma turma",
            description = "Requer autenticação e tipo de usuário 'ADMIN'",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "204",
                    description = "Turma apagada com sucesso"),
            @ApiResponse(responseCode = "401",
                    description = "Credenciais de acesso inválidas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "403",
                    description = "Usuário autenticado não é 'ADMIN'",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404",
                    description = "Turma a ser apagada não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClass(@PathVariable("id") Long classId) {
        log.info("Deleting class: {}", classId);
        classController.deleteClass(classId);
        log.info("Deleted class: {}", classId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Adiciona um aluno em uma turma",
            description = "Requer autenticação e tipo de usuário 'ADMIN'",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "204",
                    description = "Aluno adicionado a turma com sucesso"),
            @ApiResponse(responseCode = "400",
                    description = "Usuário a ser adicionado inválido",
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
                    description = "Aluno ou classe não encontrados",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PutMapping("/adiciona-aluno-em-turma/{classId}/{studentId}")
    public ResponseEntity<Void> addStudentToClass(@PathVariable("classId") Long classId, @PathVariable("studentId") Long studentId) {
        log.info("Adding student {} to class {}", studentId, classId);
        classController.addStudentToClass(classId, studentId);
        log.info("Added student {} to class {}", studentId, classId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @Operation(summary = "Adiciona um professor em uma turma",
            description = "Requer autenticação e tipo de usuário 'ADMIN'",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "204",
                    description = "Professor adicionado a turma com sucesso"),
            @ApiResponse(responseCode = "400",
                    description = "Usuário a ser adicionado inválido",
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
                    description = "Professor ou classe não encontrados",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PutMapping("/adiciona-professor-em-turma/{classId}/{teacherId}")
    public ResponseEntity<Void> addTeacherToClass(@PathVariable("classId") Long classId, @PathVariable("teacherId") Long teacherId) {
        log.info("Adding teacher {} to class {}", teacherId, classId);
        classController.addTeacherToClass(classId, teacherId);
        log.info("Added teacher {} to class {}", teacherId, classId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @Operation(summary = "Remove um aluno de uma turma",
            description = "Requer autenticação e tipo de usuário 'ADMIN'",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "204",
                    description = "Aluno removido da turma com sucesso"),
            @ApiResponse(responseCode = "400",
                    description = "Usuário a ser removido inválido",
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
                    description = "Aluno ou classe não encontrados",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PutMapping("/remove-aluno-de-turma/{classId}/{studentId}")
    public ResponseEntity<Void> removeStudentFromClass(@PathVariable("classId") Long classId, @PathVariable("studentId") Long studentId) {
        log.info("Removing student {} from class {}", studentId, classId);
        classController.removeStudentFromClass(classId, studentId);
        log.info("Removed student {} from class {}", studentId, classId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @Operation(summary = "Remove um professor de uma turma",
            description = "Requer autenticação e tipo de usuário 'ADMIN'",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "204",
                    description = "Professor removido da turma com sucesso"),
            @ApiResponse(responseCode = "400",
                    description = "Usuário a ser removido inválido",
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
                    description = "Professor ou classe não encontrados",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PutMapping("/remove-professor-de-turma/{classId}/{teacherId}")
    public ResponseEntity<Void> removeTeacherFromClass(@PathVariable("classId") Long classId, @PathVariable("teacherId") Long teacherId) {
        log.info("Removing teacher {} to class {}", teacherId, classId);
        classController.removeTeacherFromClass(classId, teacherId);
        log.info("Removed teacher {} to class {}", teacherId, classId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
