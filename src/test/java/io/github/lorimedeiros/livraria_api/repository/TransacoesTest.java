package io.github.lorimedeiros.livraria_api.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class TransacoesTest {

    /*
    Sempre que for feita uma operação de ESCRITA no banco de dados, isso é, alterar informações no banco de dados, é preciso uma trasação
    operação de escrita: delete, update
    uma operação é marcada como transação com um @Transactional

    No final de uma transação temos um commit ou rollback
    Commit   -> confirmar as alterações
    Rollback -> desfazer as alterações

    no banco de dados é visto como:
    BEGIN; --inicia a transação
    alteração qualquer/escrita
    COMMIT; --confirma as alterações
    ou
    ROLLBACK; --desfaz alterações

    é usado quando temos varias transações em sequencia
    e então quando der ruim damos rollback, deu certo é um commit
    */

    @Autowired
    AutorRepository repository;

    @Test
    @Transactional
    void transacaoSimples(){
        //salvar um livro
        //salvar o autor do livro
        //alugar livro
        //enviar email para o locatario
        //notificar que o livro saiu da livraria

        //se der erro em qualquer que seja o passo, ocorrerá rollback
        //se tudo ocorrer como esperado, ocorrerá commit
        //ou tudo, ou nada
    }
    //OBS: @Transactional só funciona em métodos public
    //OBS: sempre usado nas camadas de serviços, nunca no controller
    //OBS: quando é aberta uma janela de transação (quando é usado o @Transactional no método),
    //     os dados podem ser salvos/arualizados sem a necessidade de um repository.save(),
    //     apenas com o uso do set da entidade
}
