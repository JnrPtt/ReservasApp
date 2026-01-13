package com.jnrptt.reservasapp.reservas.domain;

import com.jnrptt.reservasapp.shared.domain.Periodo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static com.jnrptt.reservasapp.reservas.domain.EstadoReservas.ACTIVA;
import static com.jnrptt.reservasapp.reservas.domain.EstadoReservas.CANCELADA;
import static org.assertj.core.api.AssertionsForClassTypes.*;

@SpringBootTest
public class ReservaTest {

    @Test
    void al_cancelar_una_reserva_activa_su_estado_deberia_ser_cancelada() {
        LocalDateTime ahora = LocalDateTime.of(2026,1, 10, 15, 0);

        Periodo periodo = new Periodo(LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2));
        Reserva reserva = new Reserva(1, periodo , ACTIVA);

        reserva.cancelarReserva(ahora);

        assertThat(reserva.getEstado()).isEqualTo(CANCELADA);
    }

    @Test
    void no_se_puede_cancelar_una_reserva_ya_cancelada() {
        LocalDateTime ahora = LocalDateTime.of(2026,1, 10, 15, 0);

        Periodo periodo = new Periodo(LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2));
        Reserva reserva = new Reserva(1, periodo , ACTIVA);

        assertThatThrownBy(() -> {
            reserva.cancelarReserva(ahora);
            reserva.cancelarReserva(ahora);})
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("La reserva ya se encuentra cancelada");
    }

    @Test
    void no_se_puede_cancelar_una_reserva_ya_empezada(){
        LocalDateTime ahora = LocalDateTime.of(2026,1, 10, 15, 0);
        Periodo periodo = new Periodo(ahora.minusDays(1), ahora.plusDays(1));
        Reserva reserva = new Reserva(1, periodo , ACTIVA);

        assertThatThrownBy(() -> reserva.cancelarReserva(ahora))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("No se puede cancelar una reserva que ya ha empezado");
    }

    @Test
    void no_se_puede_cancelar_una_reserva_en_un_periodo_que_ya_ha_terminado(){
        LocalDateTime ahora = LocalDateTime.of(2026,1, 10, 15, 0);
        Periodo periodo = new Periodo(ahora.minusDays(2), ahora.minusDays(1));
        Reserva reserva = new Reserva(1, periodo , ACTIVA);

        assertThatThrownBy(() -> reserva.cancelarReserva(ahora))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("No se puede cancelar una reserva en un periodo que ya ha terminado");
    }

    @Test
    void se_puede_cancelar_una_reserva_activa_antes_de_que_empiece() {
        LocalDateTime ahora = LocalDateTime.of(2026, 1, 10, 10, 0);
        Periodo periodo = new Periodo(ahora.plusDays(1), ahora.plusDays(2));
        Reserva reserva = new Reserva(1, periodo, ACTIVA);

        reserva.cancelarReserva(ahora);

        assertThat(reserva.getEstado()).isEqualTo(CANCELADA);
    }

}
