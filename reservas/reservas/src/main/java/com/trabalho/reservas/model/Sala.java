package com.trabalho.reservas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // A anotação @NotBlank obriga a que o nome não seja vazio
    @NotBlank(message = "A designação da sala é obrigatória.")
    private String designacao;

    // Regra de Negócio: Capacidade tem de ser superior a zero
    @Min(value = 1, message = "A capacidade deve ser superior a zero.")
    private int capacidade;

    public Sala() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDesignacao() { return designacao; }
    public void setDesignacao(String designacao) { this.designacao = designacao; }

    public int getCapacidade() { return capacidade; }
    public void setCapacidade(int capacidade) { this.capacidade = capacidade; }
}