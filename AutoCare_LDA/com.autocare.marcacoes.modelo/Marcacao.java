package com.autocare.marcacoes.modelo;

import java.time.LocalDate;
import java.time.LocalTime;

public class Marcacao {
    private long idMarcacao;
    private LocalDate data;
    private LocalTime hora;
    private String estado;

    // Referências diretas (Lado N)
    private Viatura viatura;
    private Mecanico mecanico;

    // Relação 1:1 - Uma marcação origina uma intervenção
    private Intervencao intervencao;

    public Marcacao(long idMarcacao, LocalDate data, LocalTime hora, String estado, Viatura viatura, Mecanico mecanico) {
        this.idMarcacao = idMarcacao;
        this.data = data;
        this.hora = hora;
        this.estado = estado;
        this.viatura = viatura;
        this.mecanico = mecanico;
    }

    public LocalDate getData() { return data; }
    public LocalTime getHora() { return hora; }
    public String getEstado() { return estado; }
    public Viatura getViatura() { return viatura; }
    public Mecanico getMecanico() { return mecanico; }
    public long getIdMarcacao() { return idMarcacao; }

    public void avancarEstado() {
        if (this.estado.equals("PENDENTE")) {
            this.estado = "EM_CURSO";
        } else if (this.estado.equals("EM_CURSO")) {
            this.estado = "CONCLUIDA";
        } else {
            throw new IllegalStateException("A marcação já está concluída e não pode avançar de estado.");
        }
    }
}