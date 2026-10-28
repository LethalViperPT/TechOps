package com.autocare.gestao.modelo;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Entidade Intervenção.
 * Resulta de uma Marcação concluída (relação 1:1 Marcação -> Intervenção).
 */
public class Intervencao implements Serializable {

    private static final long serialVersionUID = 1L;

    private long idIntervencao;
    private String descricao;
    private String pecasUsadas;
    private double custo;
    private LocalDate dataConclusao;

    // Lado "1" da relação 1:1 Marcação -> Intervenção (referência ao "1" de origem)
    private Marcacao marcacao;

    public Intervencao(long idIntervencao, String descricao, String pecasUsadas, double custo,
                        LocalDate dataConclusao, Marcacao marcacao) {
        this.idIntervencao = idIntervencao;
        this.descricao = descricao;
        this.pecasUsadas = pecasUsadas;
        this.custo = custo;
        this.dataConclusao = dataConclusao;
        this.marcacao = marcacao;
    }

    public long getIdIntervencao() {
        return idIntervencao;
    }

    public void setIdIntervencao(long idIntervencao) {
        this.idIntervencao = idIntervencao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getPecasUsadas() {
        return pecasUsadas;
    }

    public void setPecasUsadas(String pecasUsadas) {
        this.pecasUsadas = pecasUsadas;
    }

    public double getCusto() {
        return custo;
    }

    public void setCusto(double custo) {
        this.custo = custo;
    }

    public LocalDate getDataConclusao() {
        return dataConclusao;
    }

    public void setDataConclusao(LocalDate dataConclusao) {
        this.dataConclusao = dataConclusao;
    }

    public Marcacao getMarcacao() {
        return marcacao;
    }

    public void setMarcacao(Marcacao marcacao) {
        this.marcacao = marcacao;
    }
}
