package com.insalud.atencionesmedicas.mapper;

import com.insalud.atencionesmedicas.dto.request.PacienteRequestDTO;
import com.insalud.atencionesmedicas.dto.response.PacienteResponseDTO;
import com.insalud.atencionesmedicas.mapper.config.MapperConfigutation;
import com.insalud.atencionesmedicas.model.Paciente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfigutation.class, uses = {PersonaMapper.class})
public interface PacienteMapper {

    @Mapping(source = "personaId", target = "persona.id")
    Paciente toEntity(PacienteRequestDTO dto);

    PacienteResponseDTO toResponse(Paciente entity);

    @Mapping(source = "personaId", target = "persona.id")
    void updateEntity(@MappingTarget Paciente entity, PacienteRequestDTO dto);

}
