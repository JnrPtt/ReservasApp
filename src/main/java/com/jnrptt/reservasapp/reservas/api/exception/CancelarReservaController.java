package com.jnrptt.reservasapp.reservas.api.exception;

import com.jnrptt.reservasapp.reservas.application.CancelarReservaUseCase;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservas")
public class CancelarReservaController {
    private final CancelarReservaUseCase cancelarReservaUseCase;

    public CancelarReservaController(CancelarReservaUseCase cancelarReservaUseCase) {
        this.cancelarReservaUseCase = cancelarReservaUseCase;
    }

    @PostMapping("/cancelar")
    public void cancelarReserva(long id){
        cancelarReservaUseCase.ejecutar(id);
    }

}
