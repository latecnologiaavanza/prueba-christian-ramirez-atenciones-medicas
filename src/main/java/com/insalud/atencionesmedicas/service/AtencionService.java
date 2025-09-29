package com.insalud.atencionesmedicas.service;

import com.insalud.atencionesmedicas.dto.request.AtencionRequestDTO;
import com.insalud.atencionesmedicas.dto.response.AtencionResponseDTO;
import com.insalud.atencionesmedicas.model.enums.Estado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;

public interface AtencionService {

    AtencionResponseDTO crearAtencion(AtencionRequestDTO request);

    AtencionResponseDTO obtenerPorId(Long id);

    Page<AtencionResponseDTO> listarTodas(Pageable pageable);

    Page<AtencionResponseDTO> listarPorPaciente(Long pacienteId, Pageable pageable);

    Page<AtencionResponseDTO> listarPorEmpleado(Long empleadoId, Pageable pageable);

    Page<AtencionResponseDTO> listarPorEstado(Estado estado, Pageable pageable);

    Page<AtencionResponseDTO> listarPorRangoFechas(LocalDateTime inicio, LocalDateTime fin, Pageable pageable);

    Page<AtencionResponseDTO> buscarPorMotivo(String motivo, Pageable pageable);

    AtencionResponseDTO actualizarAtencion(Long id, AtencionRequestDTO request);

    void eliminarAtencion(Long id);

    Page<AtencionResponseDTO> listarAtencionesDelPacienteAutenticado(String username, Pageable pageable);
}
