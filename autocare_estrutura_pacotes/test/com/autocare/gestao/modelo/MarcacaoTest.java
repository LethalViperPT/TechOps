package com.autocare.gestao.modelo;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.autocare.gestao.excecoes.EstadoInvalidoException;
import com.autocare.gestao.excecoes.ValidacaoException;

/**
 * Testes funcionais da entidade Marcação e da validação de Cliente (Tarefa 9.1).
 * Testam diretamente as entidades, sem depender de ficheiro nem de base de
 * dados — são por isso rápidos e isolados.
 */
class MarcacaoTest {

    private Marcacao criarMarcacaoDeTeste() {
        Cliente cliente = new Cliente(1, "Cliente Teste", "912345678", "201000008");
        Viatura viatura = new Viatura(1, "00-AA-00", "Renault", "Clio", 2018, cliente);
        Mecanico mecanico = new Mecanico(1, "Rui Pinto", "Motor");
        return new Marcacao(1, LocalDate.of(2026, 9, 1), LocalTime.of(10, 0), viatura, mecanico);
    }

    /** a) Caminho de sucesso: toda a marcação nasce no estado AGENDADA. */
    @Test
    void marcacaoNasceSempreAgendada() {
        Marcacao marcacao = criarMarcacaoDeTeste();
        assertEquals(EstadoMarcacao.AGENDADA, marcacao.getEstado());
    }

    /** b) Regra de negócio com transição de estado (Aula 6): AGENDADA -> CONCLUIDA é válida. */
    @Test
    void avancarEstadoParaConcluidaAlteraOEstado() {
        Marcacao marcacao = criarMarcacaoDeTeste();
        marcacao.avancarEstado(EstadoMarcacao.CONCLUIDA);
        assertEquals(EstadoMarcacao.CONCLUIDA, marcacao.getEstado());
    }

    /** c) Caso de erro esperado: tentar sair de um estado final lança EstadoInvalidoException. */
    @Test
    void avancarEstadoAPartirDeEstadoFinalLancaExcecao() {
        Marcacao marcacao = criarMarcacaoDeTeste();
        marcacao.avancarEstado(EstadoMarcacao.CONCLUIDA);

        assertThrows(EstadoInvalidoException.class, () -> marcacao.avancarEstado(EstadoMarcacao.CANCELADA));
    }

    /** c) Segundo caso de erro esperado: um NIF inválido lança ValidacaoException. */
    @Test
    void criarClienteComNifInvalidoLancaExcecao() {
        assertThrows(ValidacaoException.class,
                () -> new Cliente(1, "Cliente Inválido", "912345678", "111111111"));
    }
}
