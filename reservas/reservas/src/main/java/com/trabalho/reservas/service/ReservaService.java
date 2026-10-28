package com.trabalho.reservas.service;

import com.trabalho.reservas.model.Reserva;
import com.trabalho.reservas.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    public void guardarReserva(Reserva novaReserva) {
        // Regra de Negócio 1: A hora de fim deve ser posterior à hora de início
        if (!novaReserva.getHoraFim().isAfter(novaReserva.getHoraInicio())) {
            throw new IllegalArgumentException("Erro: A hora de fim deve ser posterior à hora de início.");
        }

        // Regra de Negócio 2: Impedir horários sobrepostos na mesma sala e data
        List<Reserva> reservasExistentes = reservaRepository.findAll();

        for (Reserva existente : reservasExistentes) {
            // Verifica se é a mesma sala e a mesma data
            if (existente.getSala().getId().equals(novaReserva.getSala().getId()) &&
                    existente.getData().equals(novaReserva.getData())) {

                // Ignora a própria reserva caso estejamos a editar uma existente
                if (novaReserva.getId() == null || !existente.getId().equals(novaReserva.getId())) {

                    // Lógica de sobreposição de horas
                    boolean sobrepoe = novaReserva.getHoraInicio().isBefore(existente.getHoraFim()) &&
                            novaReserva.getHoraFim().isAfter(existente.getHoraInicio());

                    if (sobrepoe) {
                        throw new IllegalArgumentException("Erro: A sala já possui uma reserva que se sobrepõe a este horário.");
                    }
                }
            }
        }

        // Se passar nas validações, guarda na base de dados
        reservaRepository.save(novaReserva);
    }
}