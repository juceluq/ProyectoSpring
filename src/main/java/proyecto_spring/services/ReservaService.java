package proyecto_spring.services;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import proyecto_spring.entities.Prestamo;
import proyecto_spring.entities.Reserva;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReservaService {

    List<Reserva> findAll();
    Optional<Reserva> findById(Long id);
    Reserva save (Reserva reserva);
    Optional <Reserva> update(Long id, Reserva reserva);
    Optional<Reserva> delete(Long id);

    List<Reserva> findByEstado(Reserva.EstadoReserva estado);
    List<Reserva> findByUsuarioId(Long usuarioId);
    List<Reserva> findByFechaReservaBetween(LocalDate inicio, LocalDate fin);
    List<Reserva> findByLibroId(Long libroId);
    List<Reserva> buscarPorEstadoOrdenadoPorFecha(Reserva.EstadoReserva estado);

}
