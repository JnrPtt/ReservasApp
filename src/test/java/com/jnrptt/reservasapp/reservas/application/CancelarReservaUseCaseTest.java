package com.jnrptt.reservasapp.reservas.application;

import com.jnrptt.reservasapp.reservas.domain.EstadoReservas;
import com.jnrptt.reservasapp.reservas.domain.Reserva;
import com.jnrptt.reservasapp.reservas.domain.ReservaRepository;
import com.jnrptt.reservasapp.shared.domain.Periodo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;

@SpringBootTest
public class CancelarReservaUseCaseTest {

    @Test
    void deberia_cancelar_una_reserva_correctamente() {
        ReservaRepository reservaRepositoryMock = mock(ReservaRepository.class);
        CancelarReservaUseCase cancelarReserva = new CancelarReservaUseCase(reservaRepositoryMock);

        LocalDateTime ahora = LocalDateTime.of(2026,1, 10, 15, 0);
        Periodo periodo = new Periodo(ahora.plusDays(1), ahora.plusDays(2));
        Reserva reserva = new Reserva(1, periodo, EstadoReservas.ACTIVA);

        when(reservaRepositoryMock.findById(1L)).thenReturn(reserva);

        cancelarReserva.ejecutar(1L,ahora);

        assertThat(reserva.getEstado()).isEqualTo(EstadoReservas.CANCELADA);

         verify(reservaRepositoryMock).findById(1L);
         verify(reservaRepositoryMock).guardar(reserva);
    }

    @Test
    void no_deberia_permitir_cancelar_una_reserva_que_ya_ha_sido_cancelada(){
        ReservaRepository reservaRepositoryMock = mock(ReservaRepository.class);
        CancelarReservaUseCase cancelarReserva = new CancelarReservaUseCase(reservaRepositoryMock);

        LocalDateTime ahora = LocalDateTime.of(2026,1, 10, 15, 0);
        Periodo periodo = new Periodo(ahora.minusDays(2), ahora.minusDays(1));
        Reserva reserva = new Reserva(1, periodo, EstadoReservas.CANCELADA);

        when(reservaRepositoryMock.findById(1L)).thenReturn(reserva);

        assertThatThrownBy(() -> cancelarReserva.ejecutar(1L,ahora))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("La reserva ya se encuentra cancelada");
    }

    @Test
    void no_deberia_permitir_cancelar_una_reserva_que_ya_ha_empezado(){
        ReservaRepository reservaRepositoryMock = mock(ReservaRepository.class);
        CancelarReservaUseCase cancelarReserva = new CancelarReservaUseCase(reservaRepositoryMock);

        LocalDateTime ahora = LocalDateTime.of(2026,1, 10, 15, 0);
        Periodo periodo = new Periodo(ahora.minusDays(1), ahora.plusDays(2));
        Reserva reserva = new Reserva(1, periodo, EstadoReservas.ACTIVA);

        when(reservaRepositoryMock.findById(1L)).thenReturn(reserva);

        assertThatThrownBy(() -> cancelarReserva.ejecutar(1L,ahora.minusDays(1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("No se puede cancelar una reserva que ya ha empezado");
    }

    @Test
    void no_se_puede_cancelar_una_reserva_finalizada(){
        ReservaRepository reservaRepositoryMock = mock(ReservaRepository.class);
        CancelarReservaUseCase cancelarReserva = new CancelarReservaUseCase(reservaRepositoryMock);

        LocalDateTime ahora = LocalDateTime.of(2026,1, 10, 15, 0);
        Periodo periodo = new Periodo(ahora.minusDays(2), ahora.minusDays(1));
        Reserva reserva = new Reserva(1, periodo, EstadoReservas.FINALIZADA);

        when(reservaRepositoryMock.findById(1L)).thenReturn(reserva);

        assertThatThrownBy(() -> cancelarReserva.ejecutar(1L,ahora))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("No se puede cancelar una reserva que ya ha terminado");
    }

    @Test
    void deberia_guardar_la_reserva_cuando_se_cancela_correctamente(){
        ReservaRepository reservaRepositoryMock = mock(ReservaRepository.class);
        CancelarReservaUseCase cancelarReserva = new CancelarReservaUseCase(reservaRepositoryMock);

        LocalDateTime ahora = LocalDateTime.of(2026,1, 10, 15, 0);
        Periodo periodo = new Periodo(ahora.plusDays(1), ahora.plusDays(2));
        Reserva reserva = new Reserva(1, periodo, EstadoReservas.ACTIVA);

        when(reservaRepositoryMock.findById(1L)).thenReturn(reserva);

        cancelarReserva.ejecutar(1L, ahora);

        verify(reservaRepositoryMock).findById(1L);
        verify(reservaRepositoryMock).guardar(reserva);
    }

}
