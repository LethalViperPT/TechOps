package com.autocare.marcacoes.modelo;

import java.time.LocalDate;

public class Intervencao {
    private long idIntervencao;
    private String descricao;
    private String pecasUsadas;
    private double custo;
    private LocalDate dataConclusao;
    
    // Relação 1:1 com Marcação
    private Marcacao marcacao;

    public Intervencao(long idIntervencao, String descricao, String pecasUsadas, double custo, LocalDate dataConclusao, Marcacao marcacao) {
        this.idIntervencao = idIntervencao;
        this.descricao = descricao;
        this.pecasUsadas = pecasUsadas;
        this.custo = custo;
        this.dataConclusao = dataConclusao;
        this.marcacao = marcacao;
    }
}