package com.autocare.gestao.modelo;

import com.autocare.gestao.excecoes.ValidacaoException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidade Cliente.
 * Um cliente pode possuir várias viaturas (relação 1:N com Viatura).
 * Valida o formato do nif e do contacto (Tarefa 6.2) sempre que estes
 * são definidos, tanto no construtor como nos setters.
 */
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    private long idCliente;
    private String nome;
    private String contacto;
    private String nif;

    // Lado "1" da relação 1:N Cliente -> Viatura
    private List<Viatura> viaturas;

    public Cliente(long idCliente, String nome, String contacto, String nif) {
        this.idCliente = idCliente;
        this.nome = nome;
        setContacto(contacto);
        setNif(nif);
        this.viaturas = new ArrayList<>();
    }

    public long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(long idCliente) {
        this.idCliente = idCliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getContacto() {
        return contacto;
    }

    /**
     * Aceita apenas números de telefone/telemóvel portugueses: 9 dígitos,
     * a começar por 2, 3 ou 9 (ex.: 912345678, 225551234).
     */
    public void setContacto(String contacto) {
        if (contacto == null || !contacto.matches("^[239]\\d{8}$")) {
            throw new ValidacaoException(
                    "Contacto inválido: \"" + contacto + "\". Deve ter 9 dígitos e começar por 2, 3 ou 9.");
        }
        this.contacto = contacto;
    }

    public String getNif() {
        return nif;
    }

    /**
     * Valida o NIF português: 9 dígitos e dígito de controlo correto
     * (algoritmo de módulo 11 usado pela Autoridade Tributária).
     */
    public void setNif(String nif) {
        if (!nifValido(nif)) {
            throw new ValidacaoException("NIF inválido: \"" + nif + "\".");
        }
        this.nif = nif;
    }

    private static boolean nifValido(String nif) {
        if (nif == null || !nif.matches("\\d{9}")) {
            return false;
        }
        int soma = 0;
        for (int i = 0; i < 8; i++) {
            soma += Character.getNumericValue(nif.charAt(i)) * (9 - i);
        }
        int resto = soma % 11;
        int digitoControlo = (resto < 2) ? 0 : 11 - resto;
        return digitoControlo == Character.getNumericValue(nif.charAt(8));
    }

    public List<Viatura> getViaturas() {
        return viaturas;
    }

    public void adicionarViatura(Viatura viatura) {
        this.viaturas.add(viatura);
    }
}
