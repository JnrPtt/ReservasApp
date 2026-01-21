package com.jnrptt.reservasapp.reservas.api;

import com.jnrptt.reservasapp.reservas.application.ListarReservasUseCase;
import com.jnrptt.reservasapp.reservas.domain.Reserva;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reservas")
public class ListarReservaController {
    private final ListarReservasUseCase listarReservasUseCase;

    public ListarReservaController(ListarReservasUseCase listarReservasUseCase) {
        this.listarReservasUseCase = listarReservasUseCase;
    }

    @GetMapping
    public List<Reserva> listarReservas(){
        return listarReservasUseCase.ejecutar();
    }
}
