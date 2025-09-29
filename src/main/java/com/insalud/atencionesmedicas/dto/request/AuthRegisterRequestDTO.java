package com.insalud.atencionesmedicas.dto.request;

import com.insalud.atencionesmedicas.model.enums.Rol;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthRegisterRequestDTO {

    @NotBlank(message = "El usuario es obligatorio")
    private String usuario;

    @NotBlank(message = "La contraseña es obligatoria")
    private String contrasena;

    @NotNull(message = "El rol es obligatorio")
    private Rol rol;

    @NotNull(message = "La persona es obligatoria")
    private Long personaId;
}