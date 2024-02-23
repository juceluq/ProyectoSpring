package proyecto_spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import proyecto_spring.entities.Actividad;

public interface ActividadRepository extends JpaRepository<Actividad, Long> {
}
