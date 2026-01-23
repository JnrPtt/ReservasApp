package com.jnrptt.reservasapp.reservas.api.exception;

import com.jnrptt.reservasapp.reservas.domain.exception.NoExistenReservasException;
import com.jnrptt.reservasapp.reservas.domain.exception.NoSePuedeCrearSiElPeriodoTerminoException;
import com.jnrptt.reservasapp.reservas.domain.exception.ReservaNoEncontradaException;
import com.jnrptt.reservasapp.reservas.domain.exception.SolapamientoReservaException;
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
    @ResponseStatus(HttpStatus.OK)
    public Map <String, String> handleNoExistenReservas(NoExistenReservasException ex) {
        return Map.of("error", ex.getMessage());
    }

    @ExceptionHandler(SolapamientoReservaException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map <String, String> handleSolapamientoReserva(SolapamientoReservaException ex) {
        return Map.of("error", ex.getMessage());
    }

    @ExceptionHandler(NoSePuedeCrearSiElPeriodoTerminoException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map <String, String> handleNoSePuedeCrearSiElPeriodoTermino(NoSePuedeCrearSiElPeriodoTerminoException ex) {
        return Map.of("error", ex.getMessage());
    }
}
