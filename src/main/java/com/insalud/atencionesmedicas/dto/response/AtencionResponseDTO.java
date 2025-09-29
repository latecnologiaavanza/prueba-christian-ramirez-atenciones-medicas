package com.insalud.atencionesmedicas.dto.response;

import com.insalud.atencionesmedicas.model.enums.Estado;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AtencionResponseDTO {
    private Long id;
    private LocalDateTime fecha;
    private String motivo;
    private Estado estado;
    private PacienteResponseDTO paciente;
    private EmpleadoResponseDTO empleado;
}
