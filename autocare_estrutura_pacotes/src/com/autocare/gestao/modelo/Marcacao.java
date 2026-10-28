package com.autocare.gestao.modelo;

import com.autocare.gestao.excecoes.EstadoInvalidoException;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Entidade Marcação.
 * Pertence a uma Viatura e a um Mecânico e pode originar, no máximo,
 * uma Intervenção (relação 1:1 Marcação -> Intervenção).
 *
 * O estado da marcação (Tarefa 6.1) só pode ser alterado através do método
 * avancarEstado(...): não existe um setEstado(...) público, exatamente para
 * impedir que código externo o altere diretamente e ignore as regras de
 * transição.
 */
public class Marcacao implements Serializable {

    private static final long serialVersionUID = 1L;

    private long idMarcacao;
    private LocalDate data;
    private LocalTime hora;
    private EstadoMarcacao estado;

    private Viatura viatura;
    private Mecanico mecanico;
    private Intervencao intervencao;

    public Marcacao(long idMarcacao, LocalDate data, LocalTime hora, Viatura viatura, Mecanico mecanico) {
        this.idMarcacao = idMarcacao;
        this.data = data;
        this.hora = hora;
        this.viatura = viatura;
        this.mecanico = mecanico;
        this.estado = EstadoMarcacao.AGENDADA; // toda a marcação nasce agendada
    }

    /**
     * Usado exclusivamente pela camada repositorio (Tarefa 8.1) para
     * reconstruir uma Marcação já existente na base de dados, com o estado
     * que lá estiver guardado. Ao contrário do construtor normal, não força
     * o estado inicial AGENDADA — isto não é uma transição de negócio,
     * é apenas carregar algo que já existia.
     */
    public static Marcacao reconstruir(long idMarcacao, LocalDate data, LocalTime hora,
                                        EstadoMarcacao estado, Viatura viatura, Mecanico mecanico) {
        Marcacao marcacao = new Marcacao(idMarcacao, data, hora, viatura, mecanico);
        marcacao.estado = estado;
        return marcacao;
    }

    /**
     * Única forma de alterar o estado da marcação. Só permite as transições
     * AGENDADA -> CONCLUIDA e AGENDADA -> CANCELADA. A partir de um estado
     * final (CONCLUIDA ou CANCELADA), qualquer transição é rejeitada.
     */
    public void avancarEstado(EstadoMarcacao novoEstado) {
        boolean transicaoValida =
                estado == EstadoMarcacao.AGENDADA
                        && (novoEstado == EstadoMarcacao.CONCLUIDA || novoEstado == EstadoMarcacao.CANCELADA);

        if (!transicaoValida) {
            throw new EstadoInvalidoException(
                    "Não é possível passar a marcação de " + estado + " para " + novoEstado + ".");
        }
        this.estado = novoEstado;
    }

    public long getIdMarcacao() {
        return idMarcacao;
    }

    public void setIdMarcacao(long idMarcacao) {
        this.idMarcacao = idMarcacao;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public EstadoMarcacao getEstado() {
        return estado;
    }

    public Viatura getViatura() {
        return viatura;
    }

    public void setViatura(Viatura viatura) {
        this.viatura = viatura;
    }

    public Mecanico getMecanico() {
        return mecanico;
    }

    public void setMecanico(Mecanico mecanico) {
        this.mecanico = mecanico;
    }

    public Intervencao getIntervencao() {
        return intervencao;
    }

    public void setIntervencao(Intervencao intervencao) {
        this.intervencao = intervencao;
    }
}
