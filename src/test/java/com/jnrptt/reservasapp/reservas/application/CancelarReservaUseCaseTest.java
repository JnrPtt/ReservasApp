package com.jnrptt.reservasapp.reservas.application;

import com.jnrptt.reservasapp.reservas.domain.EstadoReservas;
import com.jnrptt.reservasapp.reservas.domain.Reserva;
import com.jnrptt.reservasapp.reservas.domain.ReservaRepository;
import com.jnrptt.reservasapp.shared.domain.Periodo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.Mockito.*;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@SpringBootTest
public class CancelarReservaUseCaseTest {

    private static Clock fixedClock(LocalDateTime dateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = dateTime.atZone(zone).toInstant();
        return Clock.fixed(instant, zone);
    }

    @Test
    void deberia_cancelar_una_reserva_correctamente() {
        ReservaRepository reservaRepositoryMock = mock(ReservaRepository.class);

        LocalDateTime ahora = LocalDateTime.of(2026, 1, 10, 15, 0);
        CancelarReservaUseCase cancelarReserva = new CancelarReservaUseCase(reservaRepositoryMock, fixedClock(ahora));

        Periodo periodo = new Periodo(ahora.plusDays(1), ahora.plusDays(2));
        Reserva reserva = new Reserva(periodo, EstadoReservas.ACTIVA);

        when(reservaRepositoryMock.findById(1L)).thenReturn(Optional.of(reserva));

        cancelarReserva.ejecutar(1L);

        assertThat(reserva.getEstado()).isEqualTo(EstadoReservas.CANCELADA);

        verify(reservaRepositoryMock).findById(1L);
        verify(reservaRepositoryMock).save(reserva);
    }

    @Test
    void no_deberia_permitir_cancelar_una_reserva_que_ya_ha_sido_cancelada() {
        ReservaRepository reservaRepositoryMock = mock(ReservaRepository.class);

        LocalDateTime ahora = LocalDateTime.of(2026, 1, 10, 15, 0);
        CancelarReservaUseCase cancelarReserva = new CancelarReservaUseCase(reservaRepositoryMock, fixedClock(ahora));

        Periodo periodo = new Periodo(ahora.minusDays(2), ahora.minusDays(1));
        Reserva reserva = new Reserva(periodo, EstadoReservas.CANCELADA);

        when(reservaRepositoryMock.findById(1L)).thenReturn(Optional.of(reserva));

        assertThatThrownBy(() -> cancelarReserva.ejecutar(1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("La reserva ya se encuentra cancelada");
    }

    @Test
    void no_deberia_permitir_cancelar_una_reserva_que_ya_ha_empezado() {
        ReservaRepository reservaRepositoryMock = mock(ReservaRepository.class);

        LocalDateTime ahora = LocalDateTime.of(2026, 1, 10, 15, 0);
        Periodo periodo = new Periodo(ahora.minusDays(1), ahora.plusDays(2));
        Reserva reserva = new Reserva(periodo, EstadoReservas.ACTIVA);

        when(reservaRepositoryMock.findById(1L)).thenReturn(Optional.of(reserva));

        LocalDateTime cuandoIntentoCancelar = ahora.minusDays(1);
        CancelarReservaUseCase cancelarReserva = new CancelarReservaUseCase(
                reservaRepositoryMock,
                fixedClock(cuandoIntentoCancelar)
        );

        assertThatThrownBy(() -> cancelarReserva.ejecutar(1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("No se puede cancelar una reserva que ya ha empezado");
    }

    @Test
    void no_se_puede_cancelar_una_reserva_finalizada() {
        ReservaRepository reservaRepositoryMock = mock(ReservaRepository.class);

        LocalDateTime ahora = LocalDateTime.of(2026, 1, 10, 15, 0);
        CancelarReservaUseCase cancelarReserva = new CancelarReservaUseCase(reservaRepositoryMock, fixedClock(ahora));

        Periodo periodo = new Periodo(ahora.minusDays(2), ahora.minusDays(1));
        Reserva reserva = new Reserva(periodo, EstadoReservas.FINALIZADA);

        when(reservaRepositoryMock.findById(1L)).thenReturn(Optional.of(reserva));

        assertThatThrownBy(() -> cancelarReserva.ejecutar(1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("No se puede cancelar una reserva que ya ha terminado");
    }

    @Test
    void deberia_guardar_la_reserva_cuando_se_cancela_correctamente() {
        ReservaRepository reservaRepositoryMock = mock(ReservaRepository.class);

        LocalDateTime ahora = LocalDateTime.of(2026, 1, 10, 15, 0);
        CancelarReservaUseCase cancelarReserva = new CancelarReservaUseCase(reservaRepositoryMock, fixedClock(ahora));

        Periodo periodo = new Periodo(ahora.plusDays(1), ahora.plusDays(2));
        Reserva reserva = new Reserva(periodo, EstadoReservas.ACTIVA);

        when(reservaRepositoryMock.findById(1L)).thenReturn(Optional.of(reserva));

        cancelarReserva.ejecutar(1L);

        verify(reservaRepositoryMock).findById(1L);
        verify(reservaRepositoryMock).save(reserva);
    }

}
