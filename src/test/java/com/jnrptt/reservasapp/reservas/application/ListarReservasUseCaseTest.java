package com.jnrptt.reservasapp.reservas.application;

import com.jnrptt.reservasapp.reservas.domain.EstadoReservas;
import com.jnrptt.reservasapp.reservas.domain.Reserva;
import com.jnrptt.reservasapp.reservas.domain.ReservaRepository;
import com.jnrptt.reservasapp.shared.domain.Periodo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ListarReservasUseCaseTest {

    @Test
    void deberia_listar_todas_las_reservas() {
        ReservaRepository reservaRepositoryMock = mock(ReservaRepository.class);
        ListarReservasUseCase listarReservasUseCase = new ListarReservasUseCase(reservaRepositoryMock);

        LocalDateTime ahora = LocalDateTime.of(2026,1, 10, 15, 0);
        Periodo periodo = new Periodo(ahora.plusDays(1), ahora.plusDays(4));
        List<Reserva> reservas = List.of(new Reserva(periodo, EstadoReservas.ACTIVA), new Reserva(periodo, EstadoReservas.CANCELADA));

        when(reservaRepositoryMock.findAll()).thenReturn(reservas);

        List<Reserva> resultado = listarReservasUseCase.ejecutar();

        assertThat(resultado).isNotNull();
        assertThat(resultado).isEqualTo(reservas);
    }

    @Test
    void deberia_devolver_una_lista_vacia_si_no_hay_reservas(){
        ReservaRepository reservaRepositoryMock = mock(ReservaRepository.class);
        ListarReservasUseCase listarReservasUseCase = new ListarReservasUseCase(reservaRepositoryMock);

        when(reservaRepositoryMock.findAll()).thenReturn(List.of());

        assertThatThrownBy(() -> listarReservasUseCase.ejecutar())
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("No hay reservas registradas");
    }
}
