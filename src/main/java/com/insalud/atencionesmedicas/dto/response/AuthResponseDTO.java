package com.insalud.atencionesmedicas.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDTO {

    private String mensaje;

    private String token;

    private String tipoToken;

    public AuthResponseDTO(String mensaje) {
        this.mensaje = mensaje;
    }

    public AuthResponseDTO(String token, String tipoToken) {
        this.token = token;
        this.tipoToken = tipoToken;
        this.mensaje = "Login exitoso";
    }
}
