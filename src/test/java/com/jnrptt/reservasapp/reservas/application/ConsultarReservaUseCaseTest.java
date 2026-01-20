package com.jnrptt.reservasapp.reservas.application;

import com.jnrptt.reservasapp.reservas.domain.EstadoReservas;
import com.jnrptt.reservasapp.reservas.domain.Reserva;
import com.jnrptt.reservasapp.reservas.domain.ReservaRepository;
import com.jnrptt.reservasapp.reservas.domain.exception.ReservaNoEncontradaException;
import com.jnrptt.reservasapp.shared.domain.Periodo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ConsultarReservaUseCaseTest {

    @Test
    void deberia_consultar_una_reserva_por_id() {
        ReservaRepository reservaRepositoryMock = mock(ReservaRepository.class);
        ConsultarReservaUseCase consultarReservaUseCase = new ConsultarReservaUseCase(reservaRepositoryMock);

        LocalDateTime ahora = LocalDateTime.of(2026,1, 10, 15, 0);
        Periodo periodo = new Periodo(ahora.plusDays(1), ahora.plusDays(2));
        Reserva reserva = new Reserva(periodo, EstadoReservas.ACTIVA);

        when(reservaRepositoryMock.findById(1L)).thenReturn(Optional.of(reserva));

        Reserva resultado = consultarReservaUseCase.consultarPorId(1L);

        assertThat(resultado).isNotNull();
        assertThat(resultado).isEqualTo(reserva);
    }

    @Test
    void deberia_lanzar_una_excepcion_si_la_reserva_no_existe() {
        ReservaRepository reservaRepositoryMock = mock(ReservaRepository.class);
        ConsultarReservaUseCase consultarReservaUseCase = new ConsultarReservaUseCase(reservaRepositoryMock);

        when(reservaRepositoryMock.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> consultarReservaUseCase.consultarPorId(1L))
                .isInstanceOf(ReservaNoEncontradaException.class)
                .hasMessage("No existe la reserva con el id 1");
    }

    @Test
    void solo_se_deberia_hacer_una_consulta_por_id(){
        ReservaRepository reservaRepositoryMock = mock(ReservaRepository.class);
        ConsultarReservaUseCase consultarReservaUseCase = new ConsultarReservaUseCase(reservaRepositoryMock);

        LocalDateTime ahora = LocalDateTime.of(2026,1, 10, 15, 0);
        Periodo periodo = new Periodo(ahora.plusDays(1), ahora.plusDays(2));
        Reserva reserva = new Reserva(periodo, EstadoReservas.ACTIVA);

        when(reservaRepositoryMock.findById(1L)).thenReturn(Optional.of(reserva));

        consultarReservaUseCase.consultarPorId(1L);
        verify(reservaRepositoryMock, times(1)).findById(1L);
    }


}
