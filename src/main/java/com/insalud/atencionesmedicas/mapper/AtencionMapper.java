package com.insalud.atencionesmedicas.mapper;

import com.insalud.atencionesmedicas.dto.request.AtencionRequestDTO;
import com.insalud.atencionesmedicas.dto.response.AtencionResponseDTO;
import com.insalud.atencionesmedicas.mapper.config.MapperConfigutation;
import com.insalud.atencionesmedicas.model.Atencion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfigutation.class, uses = {PacienteMapper.class, EmpleadoMapper.class})
public interface AtencionMapper {

    @Mapping(source = "pacienteId", target = "paciente.id")
    @Mapping(source = "empleadoId", target = "empleado.id")
    Atencion toEntity(AtencionRequestDTO dto);

    AtencionResponseDTO toResponse(Atencion entity);

    @Mapping(source = "pacienteId", target = "paciente.id")
    @Mapping(source = "empleadoId", target = "empleado.id")
    void updateEntity(@MappingTarget Atencion entity, AtencionRequestDTO dto);

}
