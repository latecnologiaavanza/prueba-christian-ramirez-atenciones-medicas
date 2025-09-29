package com.insalud.atencionesmedicas.service;

import com.insalud.atencionesmedicas.dto.request.PacienteRequestDTO;
import com.insalud.atencionesmedicas.dto.response.PacienteResponseDTO;
import com.insalud.atencionesmedicas.model.enums.Estado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PacienteService {

    PacienteResponseDTO crearPaciente(PacienteRequestDTO request);

    PacienteResponseDTO obtenerPorId(Long id);

    PacienteResponseDTO actualizarPaciente(Long id, PacienteRequestDTO request);

    void eliminarPaciente(Long id);

    Page<PacienteResponseDTO> listarPacientes(Pageable pageable);

    Page<PacienteResponseDTO> listarPacientesPorEstado(Estado estado, Pageable pageable);

    List<PacienteResponseDTO> listarTodosActivos();
}
