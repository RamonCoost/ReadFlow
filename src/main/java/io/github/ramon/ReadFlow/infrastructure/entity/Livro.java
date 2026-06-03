package io.github.ramon.ReadFlow.infrastructure.entity;

import io.github.ramon.ReadFlow.infrastructure.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "livro")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo")
    private String titulo;
    @Column(name = "autor")
    private String autor;
    @Column(name = "totalPaginas")
    private int totalPaginas;
    @Column(name = "paginasLidas")
    private int paginasLidas;
    @Column(name = "statusLeitura")
    private Status statusLeitura;
}
