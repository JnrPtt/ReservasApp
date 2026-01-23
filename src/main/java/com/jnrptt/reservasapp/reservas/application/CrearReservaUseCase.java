package com.jnrptt.reservasapp.reservas.application;

import com.jnrptt.reservasapp.reservas.domain.Reserva;
import com.jnrptt.reservasapp.reservas.domain.ReservaRepository;
import com.jnrptt.reservasapp.reservas.domain.exception.NoSePuedeCrearSiElPeriodoTerminoException;
import com.jnrptt.reservasapp.reservas.domain.exception.SolapamientoReservaException;
import com.jnrptt.reservasapp.shared.domain.Periodo;

import java.time.Clock;
import java.time.LocalDateTime;

public class CrearReservaUseCase {

    private final ReservaRepository reservaRepository;
    private final Clock clock;

    public CrearReservaUseCase(ReservaRepository reservaRepository) {
        this(reservaRepository, Clock.systemDefaultZone());
    }

    public CrearReservaUseCase(ReservaRepository reservaRepository, Clock clock) {
        this.reservaRepository = reservaRepository;
        this.clock = clock;
    }

    public Reserva ejecutar(Reserva reserva) {
        LocalDateTime ahora = LocalDateTime.now(clock);
        validarSiPeriodoYaTermino(reserva, ahora);
        validarNoHaySolapamiento(reserva.getPeriodo());
        return reservaRepository.save(reserva);
    }

    private void validarNoHaySolapamiento(Periodo periodo) {
        if (reservaRepository.existeReservaSolapada(periodo)) {
            throw new SolapamientoReservaException("Ya existe una reserva en el periodo seleccionado");
        }
    }

    private void validarSiPeriodoYaTermino(Reserva reserva, LocalDateTime ahora) {
        if (reserva.getPeriodo().getFin().isBefore(ahora)) {
            throw new NoSePuedeCrearSiElPeriodoTerminoException(
                    "No se puede crear una reserva en un periodo que ya ha terminado"
            );
        }
    }
}