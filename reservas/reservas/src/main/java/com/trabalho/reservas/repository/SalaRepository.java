package com.trabalho.reservas.repository;

import com.trabalho.reservas.model.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// No Spring Data JPA, não precisamos de escrever INSERTs ou SELECTs manuais.
// O JpaRepository já traz todos os métodos CRUD feitos!
@Repository
public interface SalaRepository extends JpaRepository<Sala, Long> {
}