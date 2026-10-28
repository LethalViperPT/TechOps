package com.autocare.marcacoes.modelo;

import java.util.ArrayList;
import java.util.List;

public class Cliente {
    private long idCliente;
    private String nome;
    private String contacto;
    private String nif;

    // Relação 1:N - Um cliente possui várias viaturas
    private List<Viatura> viaturas;

    public Cliente(long idCliente, String nome, String contacto, String nif) {
        this.idCliente = idCliente;
        this.nome = nome;
        this.contacto = contacto;
        this.nif = nif;
        this.viaturas = new ArrayList<>();
    }

    // --- Getters e Setters em falta ---
    public long getIdCliente() { return idCliente; }
    public void setIdCliente(long idCliente) { this.idCliente = idCliente; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getContacto() { return contacto; }
    public void setContacto(String contacto) { this.contacto = contacto; }

    public String getNif() { return nif; }
    public void setNif(String nif) { this.nif = nif; }

    public List<Viatura> getViaturas() { return viaturas; }
    public void setViaturas(List<Viatura> viaturas) { this.viaturas = viaturas; }
}