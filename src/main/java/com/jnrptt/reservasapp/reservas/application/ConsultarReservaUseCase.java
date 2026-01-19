package com.jnrptt.reservasapp.reservas.application;

import com.jnrptt.reservasapp.reservas.domain.Reserva;
import com.jnrptt.reservasapp.reservas.domain.ReservaRepository;

public class ConsultarReservaUseCase {
    private final ReservaRepository reservaRepository;

    public ConsultarReservaUseCase(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    public Reserva consultarPorId(long id) {
        return reservaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe la reserva con el id " + id) );
    }
}
