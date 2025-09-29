package com.insalud.atencionesmedicas.controller;

import com.insalud.atencionesmedicas.dto.request.EspecialidadRequestDTO;
import com.insalud.atencionesmedicas.dto.response.EspecialidadResponseDTO;
import com.insalud.atencionesmedicas.service.EspecialidadService;
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

@RestController
@RequestMapping("/api/especialidades")
@RequiredArgsConstructor
@Slf4j
public class EspecialidadController {

    private final EspecialidadService especialidadService;

    @Operation(summary = "Crear una nueva especialidad")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Especialidad creada"),
            @ApiResponse(responseCode = "400", description = "Request inválido")
    })
    @PostMapping
    public ResponseEntity<EspecialidadResponseDTO> crear(@Valid @RequestBody EspecialidadRequestDTO request) {
        log.info("Solicitud para crear especialidad: {}", request);
        EspecialidadResponseDTO response = especialidadService.crearEspecialidad(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Actualizar una especialidad existente")
    @PutMapping("/{id}")
    public ResponseEntity<EspecialidadResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody EspecialidadRequestDTO request
    ) {
        log.info("Solicitud para actualizar especialidad id={} con datos {}", id, request);
        return ResponseEntity.ok(especialidadService.actualizarEspecialidad(id, request));
    }

    @Operation(summary = "Eliminar una especialidad por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("Solicitud para eliminar especialidad id={}", id);
        especialidadService.eliminarEspecialidad(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Obtener una especialidad por ID")
    @GetMapping("/{id}")
    public ResponseEntity<EspecialidadResponseDTO> obtenerPorId(@PathVariable Long id) {
        log.info("Buscando especialidad con id={}", id);
        return ResponseEntity.ok(especialidadService.obtenerPorId(id));
    }

    @Operation(summary = "Listar todas las especialidades (paginadas)")
    @GetMapping
    public ResponseEntity<Page<EspecialidadResponseDTO>> listarTodas(@ParameterObject Pageable pageable) {
        log.info("Listando todas las especialidades, page={} size={}", pageable.getPageNumber(), pageable.getPageSize());
        return ResponseEntity.ok(especialidadService.listarEspecialidades(pageable));
    }

    @Operation(summary = "Buscar especialidades por nombre")
    @GetMapping("/buscar")
    public ResponseEntity<Page<EspecialidadResponseDTO>> buscarPorNombre(
            @RequestParam String nombre,
            @ParameterObject Pageable pageable
    ) {
        log.info("Buscando especialidades con nombre que contenga '{}'", nombre);
        return ResponseEntity.ok(especialidadService.buscarPorNombre(nombre, pageable));
    }

    @Operation(summary = "Listar especialidades por estado")
    @GetMapping("/estado")
    public ResponseEntity<Page<EspecialidadResponseDTO>> listarPorEstado(
            @RequestParam String estado,
            @ParameterObject Pageable pageable
    ) {
        log.info("Listando especialidades con estado={}", estado);
        return ResponseEntity.ok(especialidadService.listarPorEstado(estado, pageable));
    }
}
