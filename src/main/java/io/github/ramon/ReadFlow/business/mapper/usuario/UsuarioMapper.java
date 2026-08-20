package io.github.ramon.ReadFlow.business.mapper.usuario;

import io.github.ramon.ReadFlow.business.dto.usuario.request.CadastroUsuarioRequest;
import io.github.ramon.ReadFlow.business.dto.usuario.response.UsuarioResponse;
import io.github.ramon.ReadFlow.infrastructure.entity.usuario.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UsuarioMapper {

    @Mapping(target = "id", ignore = true)
    Usuario paraUsuario(CadastroUsuarioRequest cadastroUsuarioRequest);

    UsuarioResponse paraUsuarioResponse(Usuario usuario);
}
