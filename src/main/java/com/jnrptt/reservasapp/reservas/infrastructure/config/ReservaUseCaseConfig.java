package com.jnrptt.reservasapp.reservas.infrastructure.config;


import com.jnrptt.reservasapp.reservas.application.CancelarReservaUseCase;
import com.jnrptt.reservasapp.reservas.application.ConsultarReservaUseCase;
import com.jnrptt.reservasapp.reservas.application.CrearReservaUseCase;
import com.jnrptt.reservasapp.reservas.application.ListarReservasUseCase;
import com.jnrptt.reservasapp.reservas.domain.ReservaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReservaUseCaseConfig {

    @Bean
    public ConsultarReservaUseCase consultarReservaUseCase(ReservaRepository reservaRepository){
        return new ConsultarReservaUseCase(reservaRepository);
    }

    @Bean
    public ListarReservasUseCase listarReservasUseCase(ReservaRepository reservaRepository){
        return new ListarReservasUseCase(reservaRepository);
    }

    @Bean
    public CrearReservaUseCase crearReservaUseCase(ReservaRepository reservaRepository){
        return new CrearReservaUseCase(reservaRepository);
    }

    @Bean
    public CancelarReservaUseCase cancelarReservaUseCase(ReservaRepository reservaRepository){
        return new CancelarReservaUseCase(reservaRepository);
    }
}
