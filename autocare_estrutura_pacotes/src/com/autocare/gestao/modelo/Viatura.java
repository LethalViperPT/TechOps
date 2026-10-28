package com.autocare.gestao.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidade Viatura.
 * Pertence a um Cliente (lado "N" da relação 1:N Cliente -> Viatura)
 * e pode ter várias Marcações associadas (relação 1:N Viatura -> Marcação).
 */
public class Viatura implements Serializable {

    private static final long serialVersionUID = 1L;

    private long idViatura;
    private String matricula;
    private String marca;
    private String modelo;
    private int ano;

    // Lado "N" da relação 1:N Cliente -> Viatura (referência direta ao "1")
    private Cliente cliente;

    // Lado "1" da relação 1:N Viatura -> Marcação
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

    public long getIdViatura() {
        return idViatura;
    }

    public void setIdViatura(long idViatura) {
        this.idViatura = idViatura;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Marcacao> getMarcacoes() {
        return marcacoes;
    }

    public void adicionarMarcacao(Marcacao marcacao) {
        this.marcacoes.add(marcacao);
    }
}
