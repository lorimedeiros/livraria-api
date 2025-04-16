package io.github.lorimedeiros.livraria_api.repository;

import io.github.lorimedeiros.livraria_api.model.Autor;
import io.github.lorimedeiros.livraria_api.model.GeneroLivro;
import io.github.lorimedeiros.livraria_api.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class LivroRepositoryTest {

    @Autowired
    LivroRepository repository;

    @Autowired
    AutorRepository autorRepository;

    //salvando sem cascade
    @Test
    void salvarTest(){
        Livro livro = new Livro();
        livro.setIsbn("98887-65474");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("Outro Livro");
        livro.setDataPublicacao(LocalDate.of(1980, 1, 2));

        Autor autor = autorRepository.findById(UUID.fromString("67a73a71-8729-4c47-8657-82c935d21170")).orElse(null);
        livro.setAutor(autor);

        repository.save(livro);
    }

    //com cascade; salva só o livro mas traz o autor junto, salva ambos, sem precisar usar repository de autor
    //isso se chama operação de persistencia
    @Test
    void salvarCascadeTest(){
        Livro livro = new Livro();
        livro.setIsbn("98887-65474");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("Outro Livro");
        livro.setDataPublicacao(LocalDate.of(1980, 1, 2));

        Autor autor = new Autor();
        autor.setNome("Abraão");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(1950, 1, 15));

        livro.setAutor(autor);

        repository.save(livro);
    }

    //MAAAAS... Cascade é perigoso em produção (profissional)
    //então essa seria a forma ideal e segura, salvando manualmente
    @Test
    void salvarLivroCriandoAutorTest(){
        Livro livro = new Livro();
        livro.setIsbn("98887-65554");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("Outro Livro");
        livro.setDataPublicacao(LocalDate.of(1980, 1, 2));

        Autor autor = new Autor();
        autor.setNome("Leo");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(1950, 1, 15));

        autorRepository.save(autor);

        livro.setAutor(autor);

        repository.save(livro);
    }

    @Test
    void atualizarAutorDoLivro(){
        UUID idLivro = UUID.fromString("285eb055-fa52-43a1-8dac-520616c1659a");
        Livro livroParaAtualizarAutor = repository.findById(idLivro).orElse(null);

        UUID idAutor = UUID.fromString("eb492333-9ab4-4b27-b9ca-94ba6435af12");
        Autor autorNovo = autorRepository.findById(idAutor).orElse(null);

        livroParaAtualizarAutor.setAutor(autorNovo);
        repository.save(livroParaAtualizarAutor);
    }

    //é um getbyid padrãozão, mas tem um detalhe. Ele carrega o autor para retornar livro, isso deixa a aplicação pesada
    //a solução está dentro da classe Livro
    @Test
    void buscarLivroTest(){
        UUID idLivro = UUID.fromString("bdccf169-ab72-478c-ad61-ebc35994330f");
        Livro livro = repository.findById(idLivro).orElse(null);

        System.out.println("Livro:");
        System.out.println(livro.getTitulo());

        System.out.println("Autor:");
        System.out.println(livro.getAutor().getNome());
    }

    //mas e se quisermos trazer dados do Autor mesmo usando lazy?
    //simples, usamos @Transactional, garantindo uma aplicação mais veloz
    @Test
    @Transactional
    void buscarLivroTransactionalTest(){
        UUID idLivro = UUID.fromString("bdccf169-ab72-478c-ad61-ebc35994330f");
        Livro livro = repository.findById(idLivro).orElse(null);

        System.out.println("Livro:");
        System.out.println(livro.getTitulo());

        System.out.println("Autor:");
        System.out.println(livro.getAutor().getNome());
    }

    @Test
    void pesquisaPorTituloTest(){
        List<Livro> lista = repository.findByTitulo("Roubo da Cassa 584");
        lista.forEach(System.out::println);
    }

    @Test
    void pesquisaPorIsbnTest(){
        List<Livro> lista = repository.findByIsbn("");
        lista.forEach(System.out::println);
    }

    @Test
    void pesquisaPorTituloEPrecoTest(){
        List<Livro> lista = repository.findByTituloAndPreco("Roubo da Cassa 584", BigDecimal.valueOf(90));
        lista.forEach(System.out::println);
    }

    @Test
    void listarLivrosComQuerryJPQL(){
        var resultadoPesquisa = repository.listarTodosOrdenadoPorTituloEPreco();
        resultadoPesquisa.forEach(System.out::println);
    }

    @Test
    void listarAutoresComLivros(){
        var resultado = repository.listarAutoresDosLivros();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarTitulosDiferentesDeLivros(){
        var resultado = repository.listarTitulosDistintosLivros();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarGenerosAutoresBrasileiros(){
        var resultado = repository.listarGeneroAutoresBrasileiros();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarPorGeneroQueryParam(){
        var resultado = repository.findByGenero(GeneroLivro.FICCAO, "dataPublicacao");
        resultado.forEach(System.out::println);
    }

}
