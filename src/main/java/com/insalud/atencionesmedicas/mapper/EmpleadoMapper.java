package com.insalud.atencionesmedicas.mapper;

import com.insalud.atencionesmedicas.dto.request.EmpleadoRequestDTO;
import com.insalud.atencionesmedicas.dto.response.EmpleadoResponseDTO;
import com.insalud.atencionesmedicas.mapper.config.MapperConfigutation;
import com.insalud.atencionesmedicas.model.Empleado;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfigutation.class, uses = {PersonaMapper.class})
public interface EmpleadoMapper {

    @Mapping(source = "personaId", target = "persona.id")
    Empleado toEntity(EmpleadoRequestDTO dto);

    EmpleadoResponseDTO toResponse(Empleado entity);

    @Mapping(source = "personaId", target = "persona.id")
    void updateEntity(@MappingTarget Empleado entity, EmpleadoRequestDTO dto);

}
