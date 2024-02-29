package proyecto_spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import proyecto_spring.entities.Reserva;

import java.time.LocalDate;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findByEstado(Reserva.EstadoReserva estado);
    List<Reserva> findByUsuarioId(Long usuarioId);
    List<Reserva> findByFechaReservaBetween(LocalDate inicio, LocalDate fin);
    List<Reserva> findByLibroId(Long libroId);

    @Query("SELECT r FROM Reserva r WHERE r.estado = :estado ORDER BY r.fechaReserva DESC")
    List<Reserva> buscarPorEstadoOrdenadoPorFecha(@Param("estado") Reserva.EstadoReserva estado);

}
