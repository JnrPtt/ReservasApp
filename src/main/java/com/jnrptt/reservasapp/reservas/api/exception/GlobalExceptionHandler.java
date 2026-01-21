package com.jnrptt.reservasapp.reservas.api.exception;

import com.jnrptt.reservasapp.reservas.domain.exception.NoExistenReservasException;
import com.jnrptt.reservasapp.reservas.domain.exception.ReservaNoEncontradaException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ReservaNoEncontradaException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map <String, String> handleReservaNoEncontrada(ReservaNoEncontradaException ex) {
            return Map.of("error", ex.getMessage());
    }

    @ExceptionHandler(NoExistenReservasException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map <String, String> handleNoExistenReservas(NoExistenReservasException ex) {
        return Map.of("error", ex.getMessage());
    }
}
