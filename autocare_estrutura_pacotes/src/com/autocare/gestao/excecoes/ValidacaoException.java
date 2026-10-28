package com.autocare.gestao.excecoes;

/**
 * Lançada quando um atributo de uma entidade não cumpre o formato esperado
 * (ex.: NIF ou contacto inválidos — Tarefa 6.2).
 */
public class ValidacaoException extends RuntimeException {

    public ValidacaoException(String mensagem) {
        super(mensagem);
    }
}
