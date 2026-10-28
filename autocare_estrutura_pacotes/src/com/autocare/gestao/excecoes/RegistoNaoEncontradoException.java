package com.autocare.gestao.excecoes;

/**
 * Lançada quando se tenta atualizar ou remover um registo cujo id não existe.
 * Serve exatamente para evitar que o programa "falhe silenciosamente"
 * nessas operações (Tarefa 6.3). É também o caso de erro que a Tarefa 7.2
 * identificou como merecedor de uma exceção própria, substituindo o que,
 * de outra forma, seria uma NoSuchElementException genérica do Java.
 */
public class RegistoNaoEncontradoException extends RuntimeException {

    public RegistoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
