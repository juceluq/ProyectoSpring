package proyecto_spring.repositories;

import proyecto_spring.entities.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    List<Libro> findAllByDisponibilidad(boolean disponible);
    List<Libro> findByAutorContainingIgnoreCase(String autor);
}
