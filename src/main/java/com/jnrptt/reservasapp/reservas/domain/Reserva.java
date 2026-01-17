package com.jnrptt.reservasapp.reservas.domain;

import com.jnrptt.reservasapp.shared.domain.Periodo;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reservas")
public class Reserva {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private long id;

    @Embedded
    private Periodo periodo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoReservas estado;

    public EstadoReservas getEstado() {
        return estado;
    }

    public Reserva(Periodo periodo, EstadoReservas estado) {
        this.periodo = periodo;
        this.estado = estado;
    }

    public Reserva() {
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
