package com.insalud.atencionesmedicas.controller;

import com.insalud.atencionesmedicas.dto.request.PacienteRequestDTO;
import com.insalud.atencionesmedicas.dto.response.PacienteResponseDTO;
import com.insalud.atencionesmedicas.model.enums.Estado;
import com.insalud.atencionesmedicas.service.PacienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
@RequiredArgsConstructor
@Slf4j
public class PacienteController {

    private final PacienteService pacienteService;

    @Operation(summary = "Crear un nuevo paciente")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Paciente creado"),
            @ApiResponse(responseCode = "400", description = "Request inválido"),
            @ApiResponse(responseCode = "404", description = "Persona no encontrada")
    })
    @PostMapping
    public ResponseEntity<PacienteResponseDTO> crear(@Valid @RequestBody PacienteRequestDTO request) {
        log.info("Solicitud para crear paciente con personaId={}", request.getPersonaId());
        PacienteResponseDTO response = pacienteService.crearPaciente(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Obtener un paciente por ID")
    @GetMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> obtenerPorId(@PathVariable Long id) {
        log.info("Buscando paciente con id={}", id);
        return ResponseEntity.ok(pacienteService.obtenerPorId(id));
    }

    @Operation(summary = "Actualizar un paciente existente")
    @PutMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody PacienteRequestDTO request
    ) {
        log.info("Solicitud para actualizar paciente id={} con personaId={}", id, request.getPersonaId());
        return ResponseEntity.ok(pacienteService.actualizarPaciente(id, request));
    }

    @Operation(summary = "Eliminar un paciente por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("Solicitud para eliminar paciente id={}", id);
        pacienteService.eliminarPaciente(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar todos los pacientes paginados")
    @GetMapping
    public ResponseEntity<Page<PacienteResponseDTO>> listarTodos(@ParameterObject Pageable pageable) {
        log.info("Listando pacientes, page={} size={}", pageable.getPageNumber(), pageable.getPageSize());
        return ResponseEntity.ok(pacienteService.listarPacientes(pageable));
    }

    @Operation(summary = "Listar pacientes por estado")
    @GetMapping("/estado")
    public ResponseEntity<Page<PacienteResponseDTO>> listarPorEstado(
            @RequestParam Estado estado,
            @ParameterObject Pageable pageable
    ) {
        log.info("Listando pacientes con estado={}", estado);
        return ResponseEntity.ok(pacienteService.listarPacientesPorEstado(estado, pageable));
    }

    @Operation(summary = "Listar todos los pacientes activos")
    @GetMapping("/activos")
    public ResponseEntity<List<PacienteResponseDTO>> listarActivos() {
        log.info("Listando todos los pacientes activos");
        return ResponseEntity.ok(pacienteService.listarTodosActivos());
    }
}
