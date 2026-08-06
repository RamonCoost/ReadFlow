package io.github.ramon.ReadFlow.infrastructure.repository.livro;

import io.github.ramon.ReadFlow.infrastructure.entity.livro.Livro;
import io.github.ramon.ReadFlow.infrastructure.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {


    Optional<Livro> findByTitulo(String titulo);

    List<Livro> findByAutorContainingIgnoreCase(String autor);

    List<Livro> findByStatusLeitura(Status statusLeitura);

}
