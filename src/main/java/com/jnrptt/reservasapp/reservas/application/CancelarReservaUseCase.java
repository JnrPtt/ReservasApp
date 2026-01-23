package com.jnrptt.reservasapp.reservas.application;

import com.jnrptt.reservasapp.reservas.domain.Reserva;
import com.jnrptt.reservasapp.reservas.domain.ReservaRepository;
import com.jnrptt.reservasapp.reservas.domain.exception.ReservaNoEncontradaException;

import java.time.Clock;
import java.time.LocalDateTime;

public class CancelarReservaUseCase {
    private final ReservaRepository reservaRepository;
    private final Clock clock;

    public CancelarReservaUseCase(ReservaRepository reservaRepository) {
        this(reservaRepository, Clock.systemDefaultZone());
    }

    public CancelarReservaUseCase(ReservaRepository reservaRepository, Clock clock) {
        this.reservaRepository = reservaRepository;
        this.clock = clock;
    }

    public Reserva ejecutar(long id) {  // ← Sin LocalDateTime en la firma
        LocalDateTime ahora = LocalDateTime.now(clock);  // ← Usa el clock

        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ReservaNoEncontradaException(  // ← Usa tu excepción del dominio
                        "No existe la reserva con el id " + id));

        reserva.cancelarReserva(ahora);
        return reservaRepository.save(reserva);  // ← Devuelve la reserva actualizada
    }
}