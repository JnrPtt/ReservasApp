package com.jnrptt.reservasapp.reservas.domain;

import com.jnrptt.reservasapp.shared.domain.Periodo;

import java.time.LocalDateTime;

public class Reserva {
    private long id;
    private Periodo periodo;
    private EstadoReservas estado;

    public EstadoReservas getEstado() {
        return estado;
    }

    public Reserva(long id, Periodo periodo, EstadoReservas estado) {
        this.id = id;
        this.periodo = periodo;
        this.estado = estado;
    }

    public void cancelarReserva(LocalDateTime ahora) {
        validarCancelacion(ahora);
        estado = EstadoReservas.CANCELADA;
    }

    private void validarCancelacion(LocalDateTime ahora) {
        if (estado == EstadoReservas.CANCELADA) {
            throw new IllegalArgumentException("La reserva ya se encuentra cancelada");
        }

        if (!ahora.isBefore(periodo.getInicio()) && ahora.isBefore(periodo.getFin())) {
            throw new IllegalArgumentException("No se puede cancelar una reserva que ya ha empezado");
        }

        if (estado == EstadoReservas.FINALIZADA) {
            throw new IllegalArgumentException("No se puede cancelar una reserva que ya ha terminado");
        }

        if (ahora.isAfter(periodo.getFin())) {
            throw new IllegalArgumentException("No se puede cancelar una reserva en un periodo que ya ha terminado");
        }


    }

    public Periodo getPeriodo() {
        return periodo;
    }
}
