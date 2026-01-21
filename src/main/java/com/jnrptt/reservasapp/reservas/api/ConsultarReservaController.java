package com.jnrptt.reservasapp.reservas.api;

import com.jnrptt.reservasapp.reservas.application.ConsultarReservaUseCase;
import com.jnrptt.reservasapp.reservas.domain.Reserva;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservas")
public class ConsultarReservaController {
    private final ConsultarReservaUseCase consultarReservaUseCase;

    public ConsultarReservaController(ConsultarReservaUseCase consultarReservaUseCase) {
        this.consultarReservaUseCase = consultarReservaUseCase;
    }

    @GetMapping("/{id}")
    public Reserva consultarReservaPorId(@PathVariable long id){
        return consultarReservaUseCase.consultarPorId(id);
    }
}
