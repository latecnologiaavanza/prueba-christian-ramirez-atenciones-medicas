package com.insalud.atencionesmedicas.dto.request;

import com.insalud.atencionesmedicas.model.enums.Estado;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PersonaRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Debe ser un email válido")
    private String email;

    @NotNull(message = "El estado es obligatorio")
    private Estado estado;

}
