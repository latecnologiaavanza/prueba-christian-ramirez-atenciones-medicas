package com.insalud.atencionesmedicas.dto.response;

import com.insalud.atencionesmedicas.model.enums.Estado;
import lombok.Data;

@Data
public class PersonaResponseDTO {

    private Long id;
    private String nombre;
    private String email;
    private Estado estado;

}
