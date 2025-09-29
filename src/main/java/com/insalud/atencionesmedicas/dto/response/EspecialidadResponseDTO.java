package com.insalud.atencionesmedicas.dto.response;

import com.insalud.atencionesmedicas.model.enums.Estado;
import lombok.Data;

@Data
public class EspecialidadResponseDTO {
    private Long id;
    private String nombre;
    private Estado estado;
}
