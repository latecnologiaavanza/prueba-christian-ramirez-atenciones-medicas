package com.insalud.atencionesmedicas.dto.request;

import com.insalud.atencionesmedicas.model.enums.Estado;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EspecialidadRequestDTO {
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotNull(message = "El estado es obligatorio")
    private Estado estado;
}
