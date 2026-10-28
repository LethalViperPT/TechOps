package com.autocare.marcacoes.modelo;

import java.util.ArrayList;
import java.util.List;

public class Mecanico {
    private long idMecanico;
    private String nome;
    private String especialidade;

    // Relação 1:N - Um mecânico realiza várias marcações
    private List<Marcacao> marcacoes;

    public Mecanico(long idMecanico, String nome, String especialidade) {
        this.idMecanico = idMecanico;
        this.nome = nome;
        this.especialidade = especialidade;
        this.marcacoes = new ArrayList<>();
    }

    // --- Getters e Setters ---
    public long getIdMecanico() { return idMecanico; }
    public void setIdMecanico(long idMecanico) { this.idMecanico = idMecanico; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }

    public List<Marcacao> getMarcacoes() { return marcacoes; }
    public void setMarcacoes(List<Marcacao> marcacoes) { this.marcacoes = marcacoes; }

    // Truque para o Dropdown da Interface Gráfica ficar legível
    @Override
    public String toString() {
        return nome + " (" + especialidade + ")";
    }
}