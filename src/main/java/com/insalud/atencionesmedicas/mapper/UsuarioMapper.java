package com.insalud.atencionesmedicas.mapper;

import com.insalud.atencionesmedicas.dto.request.UsuarioRequestDTO;
import com.insalud.atencionesmedicas.dto.response.UsuarioResponseDTO;
import com.insalud.atencionesmedicas.mapper.config.MapperConfigutation;
import com.insalud.atencionesmedicas.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfigutation.class, uses = {PersonaMapper.class})
public interface UsuarioMapper {

    @Mapping(source = "personaId", target = "persona.id")
    Usuario toEntity(UsuarioRequestDTO dto);

    UsuarioResponseDTO toResponse(Usuario entity);

    @Mapping(source = "personaId", target = "persona.id")
    void updateEntity(@MappingTarget Usuario entity, UsuarioRequestDTO dto);

}
