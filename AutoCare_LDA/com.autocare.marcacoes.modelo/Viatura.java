package com.autocare.marcacoes.modelo;

import java.util.ArrayList;
import java.util.List;

public class Viatura {
    private long idViatura;
    private String matricula;
    private String marca;
    private String modelo;
    private int ano;

    // Relação N:1 - Várias viaturas pertencem a um cliente
    private Cliente cliente;

    // Relação 1:N - Uma viatura é alvo de várias marcações
    private List<Marcacao> marcacoes;

    public Viatura(long idViatura, String matricula, String marca, String modelo, int ano, Cliente cliente) {
        this.idViatura = idViatura;
        this.matricula = matricula;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.cliente = cliente;
        this.marcacoes = new ArrayList<>();
    }

    // --- Getters e Setters ---
    public long getIdViatura() { return idViatura; }
    public void setIdViatura(long idViatura) { this.idViatura = idViatura; }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public int getAno() { return ano; }
    public void setAno(int ano) { this.ano = ano; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public List<Marcacao> getMarcacoes() { return marcacoes; }
    public void setMarcacoes(List<Marcacao> marcacoes) { this.marcacoes = marcacoes; }

    // Truque para o Dropdown da Interface Gráfica ficar legível
    @Override
    public String toString() {
        return marca + " " + modelo + " (" + matricula + ")";
    }
}