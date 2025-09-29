package com.insalud.atencionesmedicas.mapper;

import com.insalud.atencionesmedicas.dto.request.EspecialidadRequestDTO;
import com.insalud.atencionesmedicas.dto.response.EspecialidadResponseDTO;
import com.insalud.atencionesmedicas.mapper.config.MapperConfigutation;
import com.insalud.atencionesmedicas.model.Especialidad;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfigutation.class)
public interface EspecialidadMapper {

    Especialidad toEntity(EspecialidadRequestDTO dto);

    EspecialidadResponseDTO toResponse(Especialidad entity);

    void updateEntity(@MappingTarget Especialidad entity, EspecialidadRequestDTO dto);

}
