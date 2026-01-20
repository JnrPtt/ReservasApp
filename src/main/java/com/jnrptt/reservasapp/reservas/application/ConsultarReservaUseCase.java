package com.jnrptt.reservasapp.reservas.application;

import com.jnrptt.reservasapp.reservas.domain.Reserva;
import com.jnrptt.reservasapp.reservas.domain.ReservaRepository;
import com.jnrptt.reservasapp.reservas.domain.exception.ReservaNoEncontradaException;

public class ConsultarReservaUseCase {
    private final ReservaRepository reservaRepository;

    public ConsultarReservaUseCase(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    public Reserva consultarPorId(long id) {
        return reservaRepository.findById(id)
                .orElseThrow(() -> new ReservaNoEncontradaException("No existe la reserva con el id " + id) );
    }
}
