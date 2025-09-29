package com.insalud.atencionesmedicas.dto.request;

import com.insalud.atencionesmedicas.model.enums.Estado;
import com.insalud.atencionesmedicas.model.enums.Rol;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PacienteRequestDTO {

    @NotNull(message = "La persona es obligatoria")
    private Long personaId;

    @NotNull(message = "El rol es obligatorio")
    private Rol rol;

    @NotNull(message = "El estado es obligatorio")
    private Estado estado;

}
