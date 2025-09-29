package com.insalud.atencionesmedicas.dto.response;

import com.insalud.atencionesmedicas.model.enums.Estado;
import com.insalud.atencionesmedicas.model.enums.Rol;
import lombok.Data;

@Data
public class PacienteResponseDTO {

    private Long id;
    private Rol rol;
    private Estado estado;
    private PersonaResponseDTO persona;

}
