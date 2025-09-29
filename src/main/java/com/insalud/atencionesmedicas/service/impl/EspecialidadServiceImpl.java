package com.insalud.atencionesmedicas.service.impl;

import com.insalud.atencionesmedicas.dto.request.EspecialidadRequestDTO;
import com.insalud.atencionesmedicas.dto.response.EspecialidadResponseDTO;
import com.insalud.atencionesmedicas.exception.ResourceNotFoundException;
import com.insalud.atencionesmedicas.mapper.EspecialidadMapper;
import com.insalud.atencionesmedicas.model.Especialidad;
import com.insalud.atencionesmedicas.model.enums.Estado;
import com.insalud.atencionesmedicas.repository.EspecialidadRepository;
import com.insalud.atencionesmedicas.service.EspecialidadService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class EspecialidadServiceImpl implements EspecialidadService {

    private final EspecialidadRepository repository;
    private final EspecialidadMapper mapper;

    @Override
    public EspecialidadResponseDTO crearEspecialidad(EspecialidadRequestDTO dto) {
        Especialidad entity = mapper.toEntity(dto);
        Especialidad saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    @Override
    public EspecialidadResponseDTO actualizarEspecialidad(Long id, EspecialidadRequestDTO dto) {
        Especialidad entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Especialidad no encontrada con id " + id));
        mapper.updateEntity(entity, dto);
        Especialidad updated = repository.save(entity);
        return mapper.toResponse(updated);
    }

    @Override
    public void eliminarEspecialidad(Long id) {
        Especialidad entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Especialidad no encontrada con id " + id));
        repository.delete(entity);
    }

    @Override
    public EspecialidadResponseDTO obtenerPorId(Long id) {
        Especialidad entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Especialidad no encontrada con id " + id));
        return mapper.toResponse(entity);
    }

    @Override
    public Page<EspecialidadResponseDTO> listarEspecialidades(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    public Page<EspecialidadResponseDTO> buscarPorNombre(String nombre, Pageable pageable) {
        return repository.searchByNombre(nombre, pageable).map(mapper::toResponse);
    }

    @Override
    public Page<EspecialidadResponseDTO> listarPorEstado(String estadoStr, Pageable pageable) {
        Estado estado;
        try {
            estado = Estado.valueOf(estadoStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Estado inválido: " + estadoStr);
        }
        return repository.findByEstado(estado, pageable).map(mapper::toResponse);
    }
}
