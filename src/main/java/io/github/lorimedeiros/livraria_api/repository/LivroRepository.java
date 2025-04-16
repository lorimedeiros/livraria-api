package io.github.lorimedeiros.livraria_api.repository;

import io.github.lorimedeiros.livraria_api.model.Autor;
import io.github.lorimedeiros.livraria_api.model.GeneroLivro;
import io.github.lorimedeiros.livraria_api.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * @see LivroRepositoryTest
 * segura ctrl e clica para ir direto para a classe de Testes
 */
public interface LivroRepository extends JpaRepository<Livro, UUID> {

    //https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html
    //link para documentação do Spring Data JPA com instruções de querry methods

    List<Livro> findByAutor(Autor autor);
    List<Livro> findByTitulo(String titulo);
    List<Livro> findByIsbn(String isbn);

    //querry method duplo
    List<Livro> findByTituloAndPreco(String titulo, BigDecimal preco);
    List<Livro> findByTituloOrIsbn(String titulo, String isbn);

    //optional (caso exista) : é só trocar o tipo dos retornos, ao invés de List, Optional

    //between em datas (intervalos)
    List<Livro> findByDataPublicacaoBetween(LocalDate inicio, LocalDate fim);

    //JPQL e @Querry
    //JPQL -> referência as entidades e suas prorpiedades (ou seja, esse 'Livro' e como foi codado aqui na IDE)
    @Query(" select l " +
            "from Livro as l " +
            "order by l.titulo, l.preco ") //sim, suco do sql
    List<Livro> listarTodosOrdenadoPorTituloEPreco();
    //como pode ser observado, a grafia pode ser qualquer uma, nós que definimos, sem necessidade de finbBy e outros

    //percebe-se que o join é diferente
    @Query(" select a from Livro l join l.autor a ")
    List<Autor> listarAutoresDosLivros();
    //mesmo estando em um repository de livros não há problema em usar autor
    //a questão é, o retorno deve ser o mesmo tipo de retorno da pesquisa

    @Query(" select distinct l.titulo from Livro l ")
    List<String> listarTitulosDistintosLivros();

    @Query("""
            select l.genero
            from Livro l
            join l.autor a
            where a.nacionalidade = 'Brasileira'
            order by l.genero
            """)
    List<String> listarGeneroAutoresBrasileiros(); //para fazer quarrys maiores é só usar quebra de linha para manter a elegancia

    //parâmetros em pesquisas @query
    //o que vem depois do : é o que colocamos no @Param para relacionar
    @Query("select l from Livro l where l.genero = :nomeParametro order by :parametroOrdenacao")
    List<Livro> findByGenero(@Param("nomeParametro") GeneroLivro generoLivro,
                             @Param("parametroOrdenacao") String propriedade);

    //sem @Param mas com posição
    @Query("select l from Livro l where l.genero = ?1 order by ?2")
    List<Livro> findByGeneroPositionalParameters(GeneroLivro generoLivro, String propriedade);


    //NUNCA faça update ou delete sem WHERE isso aqui foi um teste e, sim, alterou tudo no update
    @Modifying //operações de escrita precisam de @Modifying e abrir transação
    @Transactional
    @Query("delete from Livro where genero = ?1")
    void deleteByGenero(GeneroLivro generoLivro);

    @Modifying
    @Transactional
    @Query("update Livro set dataPublicacao = ?1")
    void updateDataPublicacao(LocalDate novaData);

}