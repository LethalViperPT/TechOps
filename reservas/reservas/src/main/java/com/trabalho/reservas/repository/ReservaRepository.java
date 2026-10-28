package com.trabalho.reservas.repository;

import com.trabalho.reservas.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    // Regra de Negócio: Impede sobreposições!
    // Esta query procura se já existe alguma reserva para a mesma sala, no mesmo dia,
    // onde a hora de início colida com a hora de fim que estamos a tentar marcar.
    @Query("SELECT r FROM Reserva r WHERE r.sala.id = :salaId AND r.data = :data AND " +
            "(r.horaInicio < :horaFim AND r.horaFim > :horaInicio)")
    List<Reserva> encontrarSobreposicoes(@Param("salaId") Long salaId,
                                         @Param("data") LocalDate data,
                                         @Param("horaInicio") LocalTime horaInicio,
                                         @Param("horaFim") LocalTime horaFim);
}