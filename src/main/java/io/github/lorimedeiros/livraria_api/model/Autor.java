package io.github.lorimedeiros.livraria_api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Autor")
@Data
public class Autor {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Column(name = "nacionalidade", length = 50)
    private String nacionalidade;

    //essa list não é obrigatória no mapeamento, mas se fez posta para mostrar como dizer ao JPA que tal propriedade de Autor não é uma coluna da tabela
    @OneToMany(mappedBy = "autor") //mappedBy: "dentro da entidade livro, como está mapeado o autor?", colocamos o nome do atributo que referencia Autor em Livro
    private List<Livro> livros;

}
