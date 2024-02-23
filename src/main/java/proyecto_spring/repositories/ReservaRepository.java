package proyecto_spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import proyecto_spring.entities.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
}
