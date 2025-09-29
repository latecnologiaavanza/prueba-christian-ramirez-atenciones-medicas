package com.insalud.atencionesmedicas.service.impl;

import com.insalud.atencionesmedicas.dto.request.PacienteRequestDTO;
import com.insalud.atencionesmedicas.dto.response.PacienteResponseDTO;
import com.insalud.atencionesmedicas.exception.ResourceNotFoundException;
import com.insalud.atencionesmedicas.mapper.PacienteMapper;
import com.insalud.atencionesmedicas.model.Paciente;
import com.insalud.atencionesmedicas.model.Persona;
import com.insalud.atencionesmedicas.model.enums.Estado;
import com.insalud.atencionesmedicas.repository.PacienteRepository;
import com.insalud.atencionesmedicas.repository.PersonaRepository;
import com.insalud.atencionesmedicas.service.PacienteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PacienteServiceImpl implements PacienteService {

    private final PacienteRepository pacienteRepository;
    private final PersonaRepository personaRepository;
    private final PacienteMapper pacienteMapper;

    @Override
    public PacienteResponseDTO crearPaciente(PacienteRequestDTO request) {
        log.info("Creando paciente con personaId={}", request.getPersonaId());

        Persona persona = personaRepository.findById(request.getPersonaId())
                .orElseThrow(() -> new ResourceNotFoundException("Persona no encontrada con id " + request.getPersonaId()));

        Paciente paciente = pacienteMapper.toEntity(request);
        paciente.setPersona(persona);

        Paciente saved = pacienteRepository.save(paciente);
        return pacienteMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public PacienteResponseDTO obtenerPorId(Long id) {
        log.info("Obteniendo paciente con id={}", id);

        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado con id " + id));

        return pacienteMapper.toResponse(paciente);
    }

    @Override
    public PacienteResponseDTO actualizarPaciente(Long id, PacienteRequestDTO request) {
        log.info("Actualizando paciente id={}", id);

        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado con id " + id));

        Persona persona = personaRepository.findById(request.getPersonaId())
                .orElseThrow(() -> new ResourceNotFoundException("Persona no encontrada con id " + request.getPersonaId()));

        pacienteMapper.updateEntity(paciente, request);
        paciente.setPersona(persona);

        Paciente updated = pacienteRepository.save(paciente);
        return pacienteMapper.toResponse(updated);
    }

    @Override
    public void eliminarPaciente(Long id) {
        log.info("Eliminando paciente id={}", id);

        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado con id " + id));

        pacienteRepository.delete(paciente);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PacienteResponseDTO> listarPacientes(Pageable pageable) {
        log.info("Listando pacientes paginados, page={} size={}", pageable.getPageNumber(), pageable.getPageSize());
        return pacienteRepository.findAll(pageable).map(pacienteMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PacienteResponseDTO> listarPacientesPorEstado(Estado estado, Pageable pageable) {
        log.info("Listando pacientes por estado={} paginados", estado);
        return pacienteRepository.findByEstado(estado, pageable).map(pacienteMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PacienteResponseDTO> listarTodosActivos() {
        log.info("Listando todos los pacientes activos");
        return pacienteRepository.findByEstado(Estado.ACTIVO)
                .stream()
                .map(pacienteMapper::toResponse)
                .collect(Collectors.toList());
    }
}
