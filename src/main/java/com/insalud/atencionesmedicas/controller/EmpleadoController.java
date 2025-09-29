package com.insalud.atencionesmedicas.controller;

import com.insalud.atencionesmedicas.dto.request.EmpleadoRequestDTO;
import com.insalud.atencionesmedicas.dto.response.EmpleadoResponseDTO;
import com.insalud.atencionesmedicas.model.enums.Estado;
import com.insalud.atencionesmedicas.service.EmpleadoService;
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
@RequestMapping("/api/empleados")
@RequiredArgsConstructor
@Slf4j
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    @Operation(summary = "Crear un nuevo empleado")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Empleado creado"),
            @ApiResponse(responseCode = "400", description = "Request inválido"),
            @ApiResponse(responseCode = "404", description = "Persona no encontrada")
    })
    @PostMapping
    public ResponseEntity<EmpleadoResponseDTO> crear(@Valid @RequestBody EmpleadoRequestDTO request) {
        log.info("Solicitud para crear empleado: {}", request);
        EmpleadoResponseDTO response = empleadoService.crearEmpleado(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Obtener un empleado por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoResponseDTO> obtenerPorId(@PathVariable Long id) {
        log.info("Buscando empleado con id={}", id);
        return empleadoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Listar todos los empleados (sin paginación)")
    @GetMapping("/todos")
    public ResponseEntity<List<EmpleadoResponseDTO>> listarTodos() {
        log.info("Listando todos los empleados");
        List<EmpleadoResponseDTO> empleados = empleadoService.listarEmpleados();
        return ResponseEntity.ok(empleados);
    }

    @Operation(summary = "Listar empleados por estado (paginado)")
    @GetMapping("/estado")
    public ResponseEntity<Page<EmpleadoResponseDTO>> listarPorEstado(
            @RequestParam Estado estado,
            @ParameterObject Pageable pageable
    ) {
        log.info("Listando empleados con estado={}", estado);
        return ResponseEntity.ok(empleadoService.listarPorEstado(estado.name(), pageable));
    }

    @Operation(summary = "Actualizar un empleado existente")
    @PutMapping("/{id}")
    public ResponseEntity<EmpleadoResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody EmpleadoRequestDTO request
    ) {
        log.info("Solicitud para actualizar empleado id={} con datos {}", id, request);
        EmpleadoResponseDTO response = empleadoService.actualizarEmpleado(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Eliminar un empleado por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("Solicitud para eliminar empleado id={}", id);
        empleadoService.eliminarEmpleado(id);
        return ResponseEntity.noContent().build();
    }
}
