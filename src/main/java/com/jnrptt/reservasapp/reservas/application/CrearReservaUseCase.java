package com.jnrptt.reservasapp.reservas.application;

import com.jnrptt.reservasapp.reservas.domain.Reserva;
import com.jnrptt.reservasapp.reservas.domain.ReservaRepository;
import com.jnrptt.reservasapp.shared.domain.Periodo;

import java.time.LocalDateTime;

public class CrearReservaUseCase {

    private final ReservaRepository reservaRepository;

    public CrearReservaUseCase(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    public void ejecutar(Reserva reserva) {
        validarNoHaySolapamiento(reserva.getPeriodo());
        reservaRepository.save(reserva);
    }

    public void ejecutar(Reserva reserva, LocalDateTime ahora) {
        validarSiPeriodoYaTermino(reserva, ahora);
        validarNoHaySolapamiento(reserva.getPeriodo());
        reservaRepository.save(reserva);
    }

    private void validarNoHaySolapamiento(Periodo periodo) {
        if (reservaRepository.existeReservaSolapada(periodo)) {
            throw new IllegalArgumentException("Ya existe una reserva en el periodo seleccionado");
        }
    }

    private void validarSiPeriodoYaTermino(Reserva reserva, LocalDateTime ahora) {
        if (reserva.getPeriodo().getFin().isBefore(ahora)) {
            throw new IllegalArgumentException("No se puede crear una reserva en un periodo que ya ha terminado");
        }
    }
}
