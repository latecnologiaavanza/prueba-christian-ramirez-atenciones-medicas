package com.insalud.atencionesmedicas.service;

import com.insalud.atencionesmedicas.dto.request.AuthLoginRequestDTO;
import com.insalud.atencionesmedicas.dto.request.AuthRegisterRequestDTO;
import com.insalud.atencionesmedicas.dto.response.AuthResponseDTO;
import com.insalud.atencionesmedicas.dto.response.MessageResponseDTO;

public interface AuthService {

    MessageResponseDTO registrar(AuthRegisterRequestDTO request);

    AuthResponseDTO login(AuthLoginRequestDTO request);

}
