package com.jnrptt.reservasapp.reservas.application;

import com.jnrptt.reservasapp.reservas.domain.EstadoReservas;
import com.jnrptt.reservasapp.reservas.domain.Reserva;
import com.jnrptt.reservasapp.reservas.domain.ReservaRepository;
import com.jnrptt.reservasapp.shared.domain.Periodo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CrearReservaUseCaseTest {

    @Test
    void al_guardar_el_caso_de_uso_deberia_ejecutar_la_reserva() {
        ReservaRepository reservaRepositoryMock = mock(ReservaRepository.class);
        CrearReservaUseCase crearReservaUseCase = new CrearReservaUseCase(reservaRepositoryMock);

        Periodo periodo = new Periodo(LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2));
        Reserva reserva = new Reserva(periodo, EstadoReservas.ACTIVA);

        crearReservaUseCase.ejecutar(reserva);

        verify(reservaRepositoryMock).guardar(reserva);
    }

    @Test
    void no_se_puede_crear_una_reserva_si_hay_solapamiento_en_el_periodo_de_reserva() {
        ReservaRepository reservaRepositoryMock = mock(ReservaRepository.class);
        CrearReservaUseCase crearReservaUseCase = new CrearReservaUseCase(reservaRepositoryMock);

        Periodo periodo = new Periodo(LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2));
        Reserva reserva = new Reserva(periodo, EstadoReservas.ACTIVA);

        when(reservaRepositoryMock.existeReservaSolapada(periodo)).thenReturn(true);

        assertThatThrownBy(() -> crearReservaUseCase.ejecutar(reserva))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Ya existe una reserva en el periodo seleccionado");
    }

    @Test
    void no_se_puede_crear_una_reserva_si_su_periodo_ya_ha_terminado(){
        LocalDateTime ahora = LocalDateTime.of(2026,1, 10, 15, 0);
        Periodo periodo = new Periodo(ahora.minusDays(2), ahora.minusDays(1));

        Reserva reserva = new Reserva(periodo, EstadoReservas.ACTIVA);

        ReservaRepository reservaRepositoryMock = mock(ReservaRepository.class);

        assertThatThrownBy(() -> new CrearReservaUseCase(reservaRepositoryMock).ejecutar(reserva, ahora))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("No se puede crear una reserva en un periodo que ya ha terminado");
    }
}
