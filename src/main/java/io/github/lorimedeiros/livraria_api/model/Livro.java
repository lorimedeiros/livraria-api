package io.github.lorimedeiros.livraria_api.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "Livro")
@Data //gera getter, setters, constructor, toString, equals, hashCode
public class Livro {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "isnb", length = 20, unique = true, nullable = false)
    private String isbn;

    @Column(name = "titulo", length = 150, nullable = false)
    private String titulo;

    @Column(name = "data_publicacao")
    private LocalDate dataPublicacao;

    @Enumerated(EnumType.STRING) //para avisar ao JPA que é um enum e que quer guardar a String da enum ao invés de sua posição ordinal (0,1,...)
    @Column(name = "genero", length = 30, nullable = false)
    private GeneroLivro genero;

    @Column(name = "preco", precision = 18, scale = 2, nullable = false)
    private Double preco;

    @ManyToOne //muitos livros para um autor; many = classe atual, one = objeto da anotação
    @JoinColumn(name = "id_autor")
    private Autor autor;

}
