package com.jnrptt.reservasapp.reservas.domain;

import com.jnrptt.reservasapp.shared.domain.Periodo;

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

    public void cancelarReserva() {
        if (estado == EstadoReservas.CANCELADA) {
            throw new IllegalArgumentException("La reserva ya se encuentra cancelada");
        }
        estado = EstadoReservas.CANCELADA;
    }
}
