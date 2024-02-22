package proyecto_spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import proyecto_spring.entities.Libro;
import proyecto_spring.entities.Prestamo;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
}
