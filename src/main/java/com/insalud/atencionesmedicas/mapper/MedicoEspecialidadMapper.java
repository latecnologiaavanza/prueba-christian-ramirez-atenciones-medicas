package com.insalud.atencionesmedicas.mapper;

import com.insalud.atencionesmedicas.dto.request.MedicoEspecialidadRequestDTO;
import com.insalud.atencionesmedicas.dto.response.MedicoEspecialidadResponseDTO;
import com.insalud.atencionesmedicas.mapper.config.MapperConfigutation;
import com.insalud.atencionesmedicas.model.MedicoEspecialidad;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfigutation.class, uses = {EmpleadoMapper.class, EspecialidadMapper.class})
public interface MedicoEspecialidadMapper {

    @Mapping(source = "empleadoId", target = "empleado.id")
    @Mapping(source = "especialidadId", target = "especialidad.id")
    MedicoEspecialidad toEntity(MedicoEspecialidadRequestDTO dto);

    MedicoEspecialidadResponseDTO toResponse(MedicoEspecialidad entity);

    @Mapping(source = "empleadoId", target = "empleado.id")
    @Mapping(source = "especialidadId", target = "especialidad.id")
    void updateEntity(@MappingTarget MedicoEspecialidad entity, MedicoEspecialidadRequestDTO dto);

}
