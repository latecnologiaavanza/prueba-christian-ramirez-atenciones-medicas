package com.insalud.atencionesmedicas.service;

import com.insalud.atencionesmedicas.dto.request.EmpleadoRequestDTO;
import com.insalud.atencionesmedicas.dto.response.EmpleadoResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface EmpleadoService {

    EmpleadoResponseDTO crearEmpleado(EmpleadoRequestDTO dto);

    List<EmpleadoResponseDTO> listarEmpleados();

    Page<EmpleadoResponseDTO> listarPorEstado(String estado, Pageable pageable);

    EmpleadoResponseDTO actualizarEmpleado(Long id, EmpleadoRequestDTO dto);

    void eliminarEmpleado(Long id);

    Optional<EmpleadoResponseDTO> buscarPorId(Long id);
}
