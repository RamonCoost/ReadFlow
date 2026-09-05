package io.github.ramon.ReadFlow.infrastructure.repository.livro;

import io.github.ramon.ReadFlow.infrastructure.entity.livro.Livro;
import io.github.ramon.ReadFlow.infrastructure.entity.usuario.Usuario;
import io.github.ramon.ReadFlow.infrastructure.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {

    List<Livro> findByAutorContainingIgnoreCaseAndUsuario(String autor, Usuario usuario);

    @Query("""
            SELECT livro
            FROM Livro livro
            WHERE livro.usuario = :usuario
            AND
            (
                :status IS NULL
                 OR
                 livro.statusLeitura = :status
            )
            AND
            (
                LOWER(livro.titulo)    
                LIKE CONCAT(CONCAT("%", LOWER(:pesquisa)), "%")
                OR  
                LOWER(livro.autor)    
                LIKE CONCAT(CONCAT("%", LOWER(:pesquisa)), "%")
            )       
            """)
    Page<Livro> findTituloOrAutor(@Param("usuario") Usuario usuario, @Param("pesquisa") String pesquisa, @Param("status") Status status, Pageable pageable);

    Page<Livro> findByUsuarioAndStatusLeitura(Usuario usuario, Status status, Pageable pageable);

    Page<Livro> findByUsuario(Usuario usuario, Pageable pageable);

    Optional<Livro> findByIdAndUsuario(Long id, Usuario usuario);
}
