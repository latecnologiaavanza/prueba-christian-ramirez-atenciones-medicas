package com.insalud.atencionesmedicas.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MedicoEspecialidadRequestDTO {
    @NotNull(message = "El empleado es obligatorio")
    private Long empleadoId;

    @NotNull(message = "La especialidad es obligatoria")
    private Long especialidadId;
}
