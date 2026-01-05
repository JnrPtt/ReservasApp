package com.jnrptt.reservasapp.shared.domain;

import java.time.LocalDateTime;

public class Periodo {

    private final LocalDateTime inicio;
    private final LocalDateTime fin;

    public Periodo(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        if (fechaInicio.isAfter(fechaFin) || fechaInicio.isEqual(fechaFin)) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser posterior o igual a la fecha final");
        }

        if(fechaFin.isBefore(LocalDateTime.now())){
            throw new IllegalArgumentException("La fecha final no puede ser anterior a la fecha de inicio");
        }

        if (fechaInicio.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser anterior a la fecha actual");
        }

        this.inicio = fechaInicio;
        this.fin = fechaFin;
    }
}
