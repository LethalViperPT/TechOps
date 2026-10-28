package com.autocare.gestao.excecoes;

/**
 * Lançada quando se tenta uma transição de estado que não é permitida
 * (ex.: concluir uma marcação que já está cancelada — Tarefa 6.1).
 */
public class EstadoInvalidoException extends RuntimeException {

    public EstadoInvalidoException(String mensagem) {
        super(mensagem);
    }
}
