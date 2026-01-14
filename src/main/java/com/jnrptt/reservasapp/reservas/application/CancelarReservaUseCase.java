package com.jnrptt.reservasapp.reservas.application;

import com.jnrptt.reservasapp.reservas.domain.EstadoReservas;
import com.jnrptt.reservasapp.reservas.domain.Reserva;
import com.jnrptt.reservasapp.reservas.domain.ReservaRepository;

import java.time.LocalDateTime;

public class CancelarReservaUseCase {
    private final ReservaRepository reservaRepository;

    public CancelarReservaUseCase(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    public void ejecutar(long id, LocalDateTime ahora) {
        Reserva reserva = reservaRepository.findById(id);
        reserva.cancelarReserva(ahora);
        reservaRepository.guardar(reserva);
    }

}
