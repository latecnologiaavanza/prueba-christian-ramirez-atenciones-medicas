package com.insalud.atencionesmedicas.service;

import com.insalud.atencionesmedicas.dto.request.EspecialidadRequestDTO;
import com.insalud.atencionesmedicas.dto.response.EspecialidadResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EspecialidadService {

    EspecialidadResponseDTO crearEspecialidad(EspecialidadRequestDTO dto);

    EspecialidadResponseDTO actualizarEspecialidad(Long id, EspecialidadRequestDTO dto);

    void eliminarEspecialidad(Long id);

    EspecialidadResponseDTO obtenerPorId(Long id);

    Page<EspecialidadResponseDTO> listarEspecialidades(Pageable pageable);

    Page<EspecialidadResponseDTO> buscarPorNombre(String nombre, Pageable pageable);

    Page<EspecialidadResponseDTO> listarPorEstado(String estado, Pageable pageable);
}
