package com.autocare.gestao.excecoes;

/**
 * Lançada quando ocorre um problema ao gravar ou carregar dados de um ficheiro.
 * É uma exceção verificada (checked) de propósito: obriga quem chama os métodos
 * do repositório a decidir explicitamente como lidar com falhas de I/O,
 * em vez de as ignorar silenciosamente.
 */
public class PersistenciaException extends Exception {

    public PersistenciaException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }

    public PersistenciaException(String mensagem) {
        super(mensagem);
    }
}
