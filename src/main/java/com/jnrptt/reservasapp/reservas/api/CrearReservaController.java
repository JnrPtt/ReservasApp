package com.jnrptt.reservasapp.reservas.api;

import com.jnrptt.reservasapp.reservas.application.CrearReservaUseCase;
import com.jnrptt.reservasapp.reservas.domain.Reserva;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservas")
public class CrearReservaController {

    private final CrearReservaUseCase crearReservaUseCase;

    public CrearReservaController(CrearReservaUseCase crearReservaUseCase) {
        this.crearReservaUseCase = crearReservaUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Reserva ejecutar(@RequestBody Reserva reserva) {
        return crearReservaUseCase.ejecutar(reserva);
    }
}
