package com.trabalho.reservas.controller;

import com.trabalho.reservas.model.Reserva;
import com.trabalho.reservas.repository.ReservaRepository;
import com.trabalho.reservas.repository.SalaRepository;
import com.trabalho.reservas.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private SalaRepository salaRepository;

    // Lista as reservas e carrega as salas para a dropdown do formulário
    @GetMapping("/reservas")
    public String listarReservas(Model model) {
        model.addAttribute("reservas", reservaRepository.findAll());
        model.addAttribute("novaReserva", new Reserva());
        model.addAttribute("salas", salaRepository.findAll());
        return "reservas";
    }

    // Tenta guardar. Se a regra de negócio falhar, devolve a mensagem de erro ao utilizador
    @PostMapping("/reservas/guardar")
    public String guardarReserva(@ModelAttribute Reserva reserva, RedirectAttributes redirectAttributes) {
        try {
            reservaService.guardarReserva(reserva);
            redirectAttributes.addFlashAttribute("sucesso", "Reserva efetuada com sucesso!");
        } catch (IllegalArgumentException e) {
            // Captura o erro do ReservaService e envia para o ecrã HTML
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/reservas";
    }

    @GetMapping("/reservas/eliminar/{id}")
    public String eliminarReserva(@PathVariable Long id) {
        reservaRepository.deleteById(id);
        return "redirect:/reservas";
    }
}