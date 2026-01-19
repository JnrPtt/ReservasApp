package com.jnrptt.reservasapp.reservas.domain;

import com.jnrptt.reservasapp.shared.domain.Periodo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM Reserva r " +
            "WHERE r.periodo.inicio <= :fin " +
            "AND r.periodo.fin >= :inicio")
    boolean existeReservaSolapada(@Param("inicio") LocalDateTime inicio,
                                  @Param("fin") LocalDateTime fin);

    default boolean existeReservaSolapada(Periodo periodo) {
        return existeReservaSolapada(periodo.getInicio(), periodo.getFin());
    }
}