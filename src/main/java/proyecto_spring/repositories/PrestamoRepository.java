package proyecto_spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import proyecto_spring.entities.Libro;
import proyecto_spring.entities.User;
import proyecto_spring.entities.Prestamo;

import java.time.LocalDate;
import java.util.List;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    List<Prestamo> findByLibroId(Long libroId);
    List<Prestamo> findByUsuarioId(Long libroId);

    @Query("SELECT p FROM Prestamo p WHERE p.fechaLimite < ?1 AND p.devuelto = false")
    List<Prestamo> findPrestamosAtrasados(LocalDate fecha);

}
