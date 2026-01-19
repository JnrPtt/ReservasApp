package com.jnrptt.reservasapp.reservas.application;

import com.jnrptt.reservasapp.reservas.domain.EstadoReservas;
import com.jnrptt.reservasapp.reservas.domain.Reserva;
import com.jnrptt.reservasapp.reservas.domain.ReservaRepository;
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
}
