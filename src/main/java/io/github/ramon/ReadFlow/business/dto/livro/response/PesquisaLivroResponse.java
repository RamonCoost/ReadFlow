package io.github.ramon.ReadFlow.business.dto.livro.response;

import java.util.List;

public record PesquisaLivroResponse(String titulo,

                                    List<String> autores,

                                    String capa,

                                    String descricao,

                                    String editora,

                                    Integer totalPaginas) {
}
