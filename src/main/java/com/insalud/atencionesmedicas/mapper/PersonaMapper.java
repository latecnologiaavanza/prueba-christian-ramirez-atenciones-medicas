package com.insalud.atencionesmedicas.mapper;

import com.insalud.atencionesmedicas.dto.request.PersonaRequestDTO;
import com.insalud.atencionesmedicas.dto.response.PersonaResponseDTO;
import com.insalud.atencionesmedicas.mapper.config.MapperConfigutation;
import com.insalud.atencionesmedicas.model.Persona;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfigutation.class)
public interface PersonaMapper {

    Persona toEntity(PersonaRequestDTO dto);

    PersonaResponseDTO toResponse(Persona entity);

    void updateEntity(@MappingTarget Persona entity, PersonaRequestDTO dto);

}
