package com.trabalho.reservas.controller;

import com.trabalho.reservas.model.Sala;
import com.trabalho.reservas.repository.SalaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class SalaController {

    @Autowired
    private SalaRepository salaRepository;

    // Abre a página inicial pedida nos requisitos
    @GetMapping("/")
    public String index() {
        return "index";
    }

    // Lista as salas e prepara o formulário para adicionar uma nova
    @GetMapping("/salas")
    public String listarSalas(Model model) {
        model.addAttribute("salas", salaRepository.findAll());
        model.addAttribute("novaSala", new Sala());
        return "salas";
    }

    // Recebe os dados do formulário HTML e guarda na Base de Dados
    @PostMapping("/salas/guardar")
    public String guardarSala(@ModelAttribute Sala sala) {
        salaRepository.save(sala);
        return "redirect:/salas"; // Atualiza a página após guardar
    }

    // Elimina a sala através do ID
    @GetMapping("/salas/eliminar/{id}")
    public String eliminarSala(@PathVariable Long id) {
        salaRepository.deleteById(id);
        return "redirect:/salas";
    }
}