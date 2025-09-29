package com.insalud.atencionesmedicas.controller;

import com.insalud.atencionesmedicas.dto.request.AtencionRequestDTO;
import com.insalud.atencionesmedicas.dto.response.AtencionResponseDTO;
import com.insalud.atencionesmedicas.model.enums.Estado;
import com.insalud.atencionesmedicas.service.AtencionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/atenciones")
@RequiredArgsConstructor
@Slf4j
public class AtencionController {

    private final AtencionService atencionService;

    @Operation(summary = "Crear una nueva atención médica")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Atención creada"),
            @ApiResponse(responseCode = "400", description = "Request inválido"),
            @ApiResponse(responseCode = "404", description = "Paciente o empleado no encontrado")
    })
    @PostMapping
    public ResponseEntity<AtencionResponseDTO> crear(@Valid @RequestBody AtencionRequestDTO request) {
        log.info("Solicitud para crear atención recibida: {}", request);
        AtencionResponseDTO response = atencionService.crearAtencion(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Obtener una atención por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<AtencionResponseDTO> obtenerPorId(@PathVariable Long id) {
        log.info("Buscando atención con id={}", id);
        return ResponseEntity.ok(atencionService.obtenerPorId(id));
    }

    @Operation(summary = "Listar todas las atenciones (paginadas)")
    @GetMapping
    public ResponseEntity<Page<AtencionResponseDTO>> listarTodas(@ParameterObject Pageable pageable) {
        log.info("Listando todas las atenciones, page={} size={}", pageable.getPageNumber(), pageable.getPageSize());
        return ResponseEntity.ok(atencionService.listarTodas(pageable));
    }

    @Operation(summary = "Listar atenciones por paciente")
    @GetMapping("/pacientes/{pacienteId}")
    public ResponseEntity<Page<AtencionResponseDTO>> listarPorPaciente(
            @PathVariable Long pacienteId,
            @ParameterObject Pageable pageable
    ) {
        log.info("Listando atenciones del paciente con id={}", pacienteId);
        return ResponseEntity.ok(atencionService.listarPorPaciente(pacienteId, pageable));
    }

    @Operation(summary = "Listar atenciones por empleado")
    @GetMapping("/empleados/{empleadoId}")
    public ResponseEntity<Page<AtencionResponseDTO>> listarPorEmpleado(
            @PathVariable Long empleadoId,
            @ParameterObject Pageable pageable
    ) {
        log.info("Listando atenciones del empleado con id={}", empleadoId);
        return ResponseEntity.ok(atencionService.listarPorEmpleado(empleadoId, pageable));
    }

    @Operation(summary = "Listar atenciones por estado")
    @GetMapping("/estado")
    public ResponseEntity<Page<AtencionResponseDTO>> listarPorEstado(
            @RequestParam Estado estado,
            @ParameterObject Pageable pageable
    ) {
        log.info("Listando atenciones con estado={}", estado);
        return ResponseEntity.ok(atencionService.listarPorEstado(estado, pageable));
    }

    @Operation(summary = "Listar atenciones por rango de fechas")
    @GetMapping("/fechas")
    public ResponseEntity<Page<AtencionResponseDTO>> listarPorRangoFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin,
            @ParameterObject Pageable pageable
    ) {
        log.info("Listando atenciones entre {} y {}", inicio, fin);
        return ResponseEntity.ok(atencionService.listarPorRangoFechas(inicio, fin, pageable));
    }

    @Operation(summary = "Buscar atenciones por motivo")
    @GetMapping("/buscar")
    public ResponseEntity<Page<AtencionResponseDTO>> buscarPorMotivo(
            @RequestParam String motivo,
            @ParameterObject Pageable pageable
    ) {
        log.info("Buscando atenciones con motivo que contenga '{}'", motivo);
        return ResponseEntity.ok(atencionService.buscarPorMotivo(motivo, pageable));
    }

    @Operation(summary = "Actualizar una atención existente")
    @PutMapping("/{id}")
    public ResponseEntity<AtencionResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody AtencionRequestDTO request
    ) {
        log.info("Solicitud para actualizar atención id={} con datos {}", id, request);
        return ResponseEntity.ok(atencionService.actualizarAtencion(id, request));
    }

    @Operation(summary = "Eliminar una atención por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("Solicitud para eliminar atención id={}", id);
        atencionService.eliminarAtencion(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar atenciones del paciente autenticado")
    @GetMapping("/mias")
    @PreAuthorize("hasRole('PACIENTE')")
    public ResponseEntity<Page<AtencionResponseDTO>> listarAtencionesDeUnPacienteAutenticado(@ParameterObject Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        log.info("Listando atenciones del paciente autenticado: {}", username);

        Page<AtencionResponseDTO> response = atencionService.listarAtencionesDelPacienteAutenticado(username, pageable);
        return ResponseEntity.ok(response);
    }




}
