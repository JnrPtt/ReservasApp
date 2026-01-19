package com.jnrptt.reservasapp.reservas.application;

import com.jnrptt.reservasapp.reservas.domain.Reserva;
import com.jnrptt.reservasapp.reservas.domain.ReservaRepository;

import java.util.List;

public class ListarReservasUseCase {
    private final ReservaRepository reservaRepository;

    public ListarReservasUseCase(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    public List<Reserva> ejecutar(){
        List<Reserva> reservas = reservaRepository.findAll();
        comprobarReservasExistentes(reservas);
        return reservas;
    }

    private void comprobarReservasExistentes(List<Reserva> reservas){
        if(reservas.isEmpty()){
            throw new IllegalArgumentException("No hay reservas registradas");
        }
    }
}
