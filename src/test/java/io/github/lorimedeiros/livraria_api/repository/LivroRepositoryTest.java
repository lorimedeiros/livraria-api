package io.github.lorimedeiros.livraria_api.repository;

import io.github.lorimedeiros.livraria_api.model.Autor;
import io.github.lorimedeiros.livraria_api.model.GeneroLivro;
import io.github.lorimedeiros.livraria_api.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@SpringBootTest
class LivroRepositoryTest {

    @Autowired
    LivroRepository repository;

    @Autowired
    AutorRepository autorRepository;

    //salvando sem cascade
    @Test
    public void salvarTest(){
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
    public void salvarCascadeTest(){
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

}
