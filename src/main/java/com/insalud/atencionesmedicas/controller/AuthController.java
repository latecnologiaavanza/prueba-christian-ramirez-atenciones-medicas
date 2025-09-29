package com.insalud.atencionesmedicas.controller;

import com.insalud.atencionesmedicas.dto.request.AuthLoginRequestDTO;
import com.insalud.atencionesmedicas.dto.request.AuthRegisterRequestDTO;
import com.insalud.atencionesmedicas.dto.response.AuthResponseDTO;
import com.insalud.atencionesmedicas.dto.response.MessageResponseDTO;
import com.insalud.atencionesmedicas.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Registrar un nuevo usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuario registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o usuario ya existe")
    })
    @PostMapping("/register")
    public ResponseEntity<MessageResponseDTO> registrar(@Valid @RequestBody AuthRegisterRequestDTO request) {
        log.info("Solicitud de registro recibida para usuario={}", request.getUsuario());
        try {
            MessageResponseDTO response = authService.registrar(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException ex) {
            log.warn("Error al registrar usuario: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponseDTO(ex.getMessage()));
        }
    }

    @Operation(summary = "Iniciar sesión")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login exitoso"),
            @ApiResponse(responseCode = "400", description = "Credenciales inválidas")
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody AuthLoginRequestDTO request) {
        log.info("Solicitud de login recibida para usuario={}", request.getUsuario());
        try {
            AuthResponseDTO response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException ex) {
            log.warn("Error en login: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AuthResponseDTO(ex.getMessage()));
        }
    }
}
