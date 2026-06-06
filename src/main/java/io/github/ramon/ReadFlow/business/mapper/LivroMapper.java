package io.github.ramon.ReadFlow.business.mapper;

import io.github.ramon.ReadFlow.business.dto.request.LivroRequest;
import io.github.ramon.ReadFlow.business.dto.response.LivroResponse;
import io.github.ramon.ReadFlow.infrastructure.entity.Livro;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface LivroMapper {

    @Mapping(target = "id", ignore = true)
    Livro paraLivro(LivroRequest livroRequest);

    LivroResponse paraLivroResponse(Livro livro);

    List<LivroResponse> paraLivroResponseList(List<Livro> livros);
}
