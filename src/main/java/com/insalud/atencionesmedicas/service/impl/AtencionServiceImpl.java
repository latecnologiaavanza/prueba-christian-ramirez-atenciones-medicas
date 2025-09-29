package com.insalud.atencionesmedicas.service.impl;

import com.insalud.atencionesmedicas.dto.request.AtencionRequestDTO;
import com.insalud.atencionesmedicas.dto.response.AtencionResponseDTO;
import com.insalud.atencionesmedicas.exception.BusinessException;
import com.insalud.atencionesmedicas.exception.InvalidRequestException;
import com.insalud.atencionesmedicas.exception.ResourceNotFoundException;
import com.insalud.atencionesmedicas.mapper.AtencionMapper;
import com.insalud.atencionesmedicas.model.Atencion;
import com.insalud.atencionesmedicas.model.Empleado;
import com.insalud.atencionesmedicas.model.Paciente;
import com.insalud.atencionesmedicas.model.enums.Estado;
import com.insalud.atencionesmedicas.repository.AtencionRepository;
import com.insalud.atencionesmedicas.repository.EmpleadoRepository;
import com.insalud.atencionesmedicas.repository.PacienteRepository;
import com.insalud.atencionesmedicas.service.AtencionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class AtencionServiceImpl implements AtencionService {

    private final AtencionRepository atencionRepository;
    private final PacienteRepository pacienteRepository;
    private final EmpleadoRepository empleadoRepository;
    private final AtencionMapper atencionMapper;

    @Override
    @Transactional
    public AtencionResponseDTO crearAtencion(AtencionRequestDTO request) {
        if (request.getFecha() == null) {
            throw new InvalidRequestException("La fecha de la atención es obligatoria.");
        }

        if (request.getFecha().isBefore(LocalDateTime.now().minusMinutes(1))) {
            throw new InvalidRequestException("La fecha de la atención no puede ser en el pasado.");
        }

        Paciente paciente = pacienteRepository.findById(request.getPacienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado con id: " + request.getPacienteId()));

        Empleado empleado = empleadoRepository.findById(request.getEmpleadoId())
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado con id: " + request.getEmpleadoId()));

        Atencion atencion = atencionMapper.toEntity(request);

        atencion.setPaciente(paciente);
        atencion.setEmpleado(empleado);

        Atencion saved = atencionRepository.save(atencion);
        log.info("Atención creada. id={}", saved.getId());

        return atencionMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public AtencionResponseDTO obtenerPorId(Long id) {
        Atencion atencion = atencionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Atención no encontrada con id: " + id));
        return atencionMapper.toResponse(atencion);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AtencionResponseDTO> listarTodas(Pageable pageable) {
        return atencionRepository.findAll(pageable).map(atencionMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AtencionResponseDTO> listarPorPaciente(Long pacienteId, Pageable pageable) {
        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado con id: " + pacienteId));
        return atencionRepository.findByPaciente(paciente, pageable).map(atencionMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AtencionResponseDTO> listarPorEmpleado(Long empleadoId, Pageable pageable) {
        Empleado empleado = empleadoRepository.findById(empleadoId)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado con id: " + empleadoId));
        return atencionRepository.findByEmpleado(empleado, pageable).map(atencionMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AtencionResponseDTO> listarPorEstado(Estado estado, Pageable pageable) {
        return atencionRepository.findByEstado(estado, pageable).map(atencionMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AtencionResponseDTO> listarPorRangoFechas(LocalDateTime inicio, LocalDateTime fin, Pageable pageable) {
        if (inicio == null || fin == null) {
            throw new InvalidRequestException("Debe proporcionar fecha de inicio y fin para el filtro.");
        }
        if (fin.isBefore(inicio)) {
            throw new InvalidRequestException("La fecha fin no puede ser anterior a la fecha inicio.");
        }
        return atencionRepository.findByFechaBetween(inicio, fin, pageable).map(atencionMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AtencionResponseDTO> buscarPorMotivo(String motivo, Pageable pageable) {
        if (motivo == null || motivo.trim().isEmpty()) {
            throw new InvalidRequestException("El motivo para búsqueda no puede estar vacío.");
        }
        return atencionRepository.searchByMotivo(motivo.trim(), pageable).map(atencionMapper::toResponse);
    }

    @Override
    @Transactional
    public AtencionResponseDTO actualizarAtencion(Long id, AtencionRequestDTO request) {
        Atencion atencion = atencionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Atención no encontrada con id: " + id));

        if (atencion.getEstado() == Estado.FINALIZADO) {
            throw new BusinessException("No se puede actualizar una atención que ya está finalizada.");
        }

        if (request.getFecha() == null) {
            throw new InvalidRequestException("La fecha de la atención es obligatoria.");
        }

        atencion.setFecha(request.getFecha());
        atencion.setMotivo(request.getMotivo());
        atencion.setEstado(request.getEstado());

        if (request.getPacienteId() != null && !request.getPacienteId().equals(atencion.getPaciente().getId())) {
            Paciente paciente = pacienteRepository.findById(request.getPacienteId())
                    .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado con id: " + request.getPacienteId()));
            atencion.setPaciente(paciente);
        }

        if (request.getEmpleadoId() != null && !request.getEmpleadoId().equals(atencion.getEmpleado().getId())) {
            Empleado empleado = empleadoRepository.findById(request.getEmpleadoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado con id: " + request.getEmpleadoId()));
            atencion.setEmpleado(empleado);
        }

        Atencion updated = atencionRepository.save(atencion);
        log.info("Atención actualizada. id={}", updated.getId());
        return atencionMapper.toResponse(updated);
    }

    @Override
    @Transactional
    public void eliminarAtencion(Long id) {
        if (!atencionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Atención no encontrada con id: " + id);
        }
        atencionRepository.deleteById(id);
        log.info("Atención eliminada. id={}", id);
    }

    @Override
    public Page<AtencionResponseDTO> listarAtencionesDelPacienteAutenticado(String username, Pageable pageable) {
        Paciente paciente = pacienteRepository.findByUsuarioUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado para el usuario autenticado"));

        Page<Atencion> atenciones = atencionRepository.findByPaciente(paciente, pageable);

        return atenciones.map(atencionMapper::toResponse);
    }

}
