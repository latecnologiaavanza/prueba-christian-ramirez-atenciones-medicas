package com.insalud.atencionesmedicas.service.impl;

import com.insalud.atencionesmedicas.dto.request.EmpleadoRequestDTO;
import com.insalud.atencionesmedicas.dto.response.EmpleadoResponseDTO;
import com.insalud.atencionesmedicas.exception.ResourceNotFoundException;
import com.insalud.atencionesmedicas.mapper.EmpleadoMapper;
import com.insalud.atencionesmedicas.model.Empleado;
import com.insalud.atencionesmedicas.model.Persona;
import com.insalud.atencionesmedicas.repository.EmpleadoRepository;
import com.insalud.atencionesmedicas.repository.PersonaRepository;
import com.insalud.atencionesmedicas.service.EmpleadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmpleadoServiceImpl implements EmpleadoService {

    private final EmpleadoRepository empleadoRepository;
    private final PersonaRepository personaRepository;
    private final EmpleadoMapper empleadoMapper;

    @Override
    @Transactional
    public EmpleadoResponseDTO crearEmpleado(EmpleadoRequestDTO dto) {
        Persona persona = personaRepository.findById(dto.getPersonaId())
                .orElseThrow(() -> new ResourceNotFoundException("Persona no encontrada con id: " + dto.getPersonaId()));

        Empleado empleado = empleadoMapper.toEntity(dto);
        empleado.setPersona(persona);

        Empleado guardado = empleadoRepository.save(empleado);
        return empleadoMapper.toResponse(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmpleadoResponseDTO> listarEmpleados() {
        return empleadoRepository.findAll()
                .stream()
                .map(empleadoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EmpleadoResponseDTO> listarPorEstado(String estado, Pageable pageable) {
        Page<Empleado> page = empleadoRepository.findByEstado(
                Enum.valueOf(com.insalud.atencionesmedicas.model.enums.Estado.class, estado.toUpperCase()),
                pageable
        );
        return page.map(empleadoMapper::toResponse);
    }

    @Override
    @Transactional
    public EmpleadoResponseDTO actualizarEmpleado(Long id, EmpleadoRequestDTO dto) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado con id: " + id));

        if (!empleado.getPersona().getId().equals(dto.getPersonaId())) {
            Persona persona = personaRepository.findById(dto.getPersonaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Persona no encontrada con id: " + dto.getPersonaId()));
            empleado.setPersona(persona);
        }

        empleadoMapper.updateEntity(empleado, dto);
        Empleado actualizado = empleadoRepository.save(empleado);
        return empleadoMapper.toResponse(actualizado);
    }

    @Override
    @Transactional
    public void eliminarEmpleado(Long id) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado con id: " + id));
        empleadoRepository.delete(empleado);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EmpleadoResponseDTO> buscarPorId(Long id) {
        return empleadoRepository.findById(id)
                .map(empleadoMapper::toResponse);
    }
}
