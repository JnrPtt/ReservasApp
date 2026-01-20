package com.jnrptt.reservasapp.reservas.domain.exception;

public class ReservaNoEncontradaException extends RuntimeException {
    public ReservaNoEncontradaException(String mensaje) {
        super(mensaje);
    }
}
