package com.jnrptt.reservasapp.reservas.application;

import com.jnrptt.reservasapp.reservas.domain.Reserva;
import com.jnrptt.reservasapp.reservas.domain.ReservaRepository;

public class CrearReservaUseCase {

    private final ReservaRepository reservaRepository;

    public CrearReservaUseCase(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    public void ejecutar(Reserva reserva) {
        reservaRepository.guardar(reserva);
    }
}
