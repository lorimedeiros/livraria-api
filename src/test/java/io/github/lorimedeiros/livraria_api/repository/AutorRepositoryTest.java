package io.github.lorimedeiros.livraria_api.repository;

import io.github.lorimedeiros.livraria_api.model.Autor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class AutorRepositoryTest {

    @Autowired
    AutorRepository repository;

    @Test
    public void salvarTest(){
        Autor autor = new Autor();
        autor.setNome("José");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(1950, 1, 15));

        var autorSalvo = repository.save(autor);
        System.out.println("Autor salvo: " + autorSalvo);
    }

    @Test
    public void atualizarTest(){
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
    public void listarTest(){
        List<Autor> lista = repository.findAll();
        lista.forEach(System.out::println);
    }

    @Test
    public void countTest(){
        System.out.println("Contagem de Autores: " + repository.count());
    }

    @Test
    public void deletePorIdTest(){
        var id = UUID.fromString("fd522650-7e1c-41c3-b700-f6978391d409");
        repository.deleteById(id);
    }

    @Test
    public void deleteTest(){
        var id = UUID.fromString("c6446412-ed60-40c3-9261-9b4b7e4fd79d");

        Optional<Autor> possivelAutor = repository.findById(id);

        if (possivelAutor.isPresent()) {
            Autor autorEncontrado = possivelAutor.get();
            repository.delete(autorEncontrado);
        }
    }

}
