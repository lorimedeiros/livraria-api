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

    @Test
    public void salvarTest(){
        Livro livro = new Livro();
        livro.setIsbn("98887-84874");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("UFO");
        livro.setDataPublicacao(LocalDate.of(1980, 1, 2));

        Autor autor = autorRepository.findById(UUID.fromString("67a73a71-8729-4c47-8657-82c935d21170")).orElse(null);
        livro.setAutor(autor);

        repository.save(livro);
    }

}
