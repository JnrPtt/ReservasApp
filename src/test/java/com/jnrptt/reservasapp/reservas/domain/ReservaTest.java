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
        Periodo periodo = new Periodo(LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2));
        Reserva reserva = new Reserva(1, periodo , ACTIVA);

        reserva.cancelarReserva();

        assertThat(reserva.getEstado()).isEqualTo(CANCELADA);
    }

    @Test
    void no_se_puede_cancelar_una_reserva_ya_cancelada() {
        Periodo periodo = new Periodo(LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2));
        Reserva reserva = new Reserva(1, periodo , ACTIVA);


        assertThatThrownBy(() -> {
            reserva.cancelarReserva();
            reserva.cancelarReserva();})
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("La reserva ya se encuentra cancelada");
    }
}
