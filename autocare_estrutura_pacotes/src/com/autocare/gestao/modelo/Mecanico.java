package com.autocare.gestao.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidade Mecânico.
 * Um mecânico realiza várias marcações (relação 1:N Mecânico -> Marcação).
 */
public class Mecanico implements Serializable {

    private static final long serialVersionUID = 1L;

    private long idMecanico;
    private String nome;
    private String especialidade;

    // Lado "1" da relação 1:N Mecânico -> Marcação
    private List<Marcacao> marcacoes;

    public Mecanico(long idMecanico, String nome, String especialidade) {
        this.idMecanico = idMecanico;
        this.nome = nome;
        this.especialidade = especialidade;
        this.marcacoes = new ArrayList<>();
    }

    public long getIdMecanico() {
        return idMecanico;
    }

    public void setIdMecanico(long idMecanico) {
        this.idMecanico = idMecanico;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public List<Marcacao> getMarcacoes() {
        return marcacoes;
    }

    public void adicionarMarcacao(Marcacao marcacao) {
        this.marcacoes.add(marcacao);
    }
}
