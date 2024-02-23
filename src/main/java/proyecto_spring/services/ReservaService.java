package proyecto_spring.services;

import proyecto_spring.entities.Prestamo;
import proyecto_spring.entities.Reserva;

import java.util.List;
import java.util.Optional;

public interface ReservaService {

    List<Reserva> findAll();
    Optional<Reserva> findById(Long id);
    Reserva save (Reserva reserva);
    Optional <Reserva> update(Long id, Reserva reserva);
    Optional<Reserva> delete(Long id);


}
