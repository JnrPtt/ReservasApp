package com.jnrptt.reservasapp.shared.domain;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.*;

@SpringBootTest
public class PeriodoTest {

    @Test
    void se_puede_crear_un_periodo_valido() {
        LocalDateTime fechaInicio = LocalDateTime.now().plusDays(1);
        LocalDateTime fechaFin = LocalDateTime.now().plusDays(2);

        assertThatNoException().isThrownBy(() -> new Periodo(fechaInicio, fechaFin));
    }

    @Test
    void no_se_puede_crear_un_periodo_si_inicio_es_igual_a_fin() {
        LocalDateTime fechaInicio = LocalDateTime.now();
        LocalDateTime fechaFin = LocalDateTime.now();

        assertThatThrownBy(() -> new Periodo(fechaInicio, fechaFin))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("La fecha de inicio no puede ser posterior o igual a la fecha final");
    }
}
