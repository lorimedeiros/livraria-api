package io.github.lorimedeiros.livraria_api.repository;

import io.github.lorimedeiros.livraria_api.model.Autor;
import io.github.lorimedeiros.livraria_api.model.GeneroLivro;
import io.github.lorimedeiros.livraria_api.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class AutorRepositoryTest {

    @Autowired
    AutorRepository repository;

    @Autowired
    LivroRepository livroRepository;

    @Test
    void salvarTest(){
        Autor autor = new Autor();
        autor.setNome("José");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(1950, 1, 15));

        var autorSalvo = repository.save(autor);
        System.out.println("Autor salvo: " + autorSalvo);
    }

    @Test
    void atualizarTest(){
        var id = UUID.fromString("fd522650-7e1c-41c3-b700-f6978391d409"); //é o id que peguei no banco do autor gerado pelo test de salvar

        Optional<Autor> possivelAutor = repository.findById(id);
        if (possivelAutor.isPresent()){
            Autor autorEncontrado = possivelAutor.get();
            System.out.println("Dados do Autor: ");
            System.out.println(possivelAutor.get());

            autorEncontrado.setDataNascimento(LocalDate.of(1960, 1, 15));
            repository.save(autorEncontrado); //metodo salvar tanto salva quanto atualiza
        }

    }

    @Test
    void listarTest(){
        List<Autor> lista = repository.findAll();
        lista.forEach(System.out::println);
    }

    @Test
    void countTest(){
        System.out.println("Contagem de Autores: " + repository.count());
    }

    @Test
    void deletePorIdTest(){
        var id = UUID.fromString("fd522650-7e1c-41c3-b700-f6978391d409");
        repository.deleteById(id);
    }

    @Test
    void deleteTest(){
        var id = UUID.fromString("c6446412-ed60-40c3-9261-9b4b7e4fd79d");

        Optional<Autor> possivelAutor = repository.findById(id);

        if (possivelAutor.isPresent()) {
            Autor autorEncontrado = possivelAutor.get();
            repository.delete(autorEncontrado);
        }
    }

    @Test
    void salvarAutorComLivrosTest(){
        Autor autor = new Autor();
        autor.setNome("Joana");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(1990, 3, 17));

        Livro livro = new Livro();
        livro.setIsbn("28587-95554");
        livro.setPreco(BigDecimal.valueOf(90));
        livro.setGenero(GeneroLivro.MISTERIO);
        livro.setTitulo("Assassinato no Trem");
        livro.setDataPublicacao(LocalDate.of(2020, 1, 2));
        livro.setAutor(autor);

        Livro livro2 = new Livro();
        livro2.setIsbn("36547-95554");
        livro2.setPreco(BigDecimal.valueOf(90));
        livro2.setGenero(GeneroLivro.MISTERIO);
        livro2.setTitulo("Roubo da Cassa 584");
        livro2.setDataPublicacao(LocalDate.of(2022, 1, 2));
        livro2.setAutor(autor);

        autor.setLivros(new ArrayList<>());
        autor.getLivros().add(livro);
        autor.getLivros().add(livro2);

        repository.save(autor);
        livroRepository.saveAll(autor.getLivros()); //salva toda a lista de uma vez
    }

}
