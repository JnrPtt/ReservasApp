package com.jnrptt.reservasapp.reservas.domain.exception;

public class NoExistenReservasException extends RuntimeException {
    public NoExistenReservasException(String message) {
        super(message);
    }
}
