package io.github.lorimedeiros.livraria_api.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "Livro")
@Data
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

    @Enumerated(EnumType.STRING)
    @Column(name = "genero", length = 30, nullable = false)
    private GeneroLivro genero;

    @Column(name = "preco", precision = 5, scale = 2, nullable = false)
    private BigDecimal preco;

    //fetch = FetchType.EAGER : PADRÃO quando não especificamos; traz junto o objeto da relação
    //fetch = FetchType.LAZY  : não traz o obj da relação, tornando a operação mais leve
    //ou seja, se eu pedir qualquer informação de autor pelo livro, irá dar erro, pois não carrega autor
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_autor")
    private Autor autor;

}
