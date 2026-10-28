package com.autocare.gestao.excecoes;

/**
 * Lançada quando a comunicação com um serviço externo falha (Tarefa 8.2),
 * seja porque o pedido não foi entregue, seja porque a resposta indicou erro.
 */
public class NotificacaoExternaException extends Exception {

    public NotificacaoExternaException(String mensagem) {
        super(mensagem);
    }

    public NotificacaoExternaException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
