package com.jnrptt.reservasapp.reservas.domain;

import com.jnrptt.reservasapp.shared.domain.Periodo;

public interface ReservaRepository {
    boolean existeReservaSolapada(Periodo periodo);
    Reserva guardar(Reserva reserva);
    Reserva findById(long id);
}
