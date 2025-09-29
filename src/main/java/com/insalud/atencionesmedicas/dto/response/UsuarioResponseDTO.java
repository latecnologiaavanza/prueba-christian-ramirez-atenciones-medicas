package com.insalud.atencionesmedicas.dto.response;

import lombok.Data;

@Data
public class UsuarioResponseDTO {

    private Long id;
    private String usuario;
    private PersonaResponseDTO persona;

}
