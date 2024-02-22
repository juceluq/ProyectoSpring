package proyecto_spring.services;

import org.springframework.stereotype.Service;
import proyecto_spring.entities.Libro;
import proyecto_spring.entities.Prestamo;

import java.util.List;
import java.util.Optional;

public interface PrestamoService {

    List<Prestamo> findAll();
    Optional<Prestamo> findById(Long id);
    Prestamo save (Prestamo prestamo);
    Optional <Prestamo> update(Long id, Prestamo prestamo);
    Optional<Prestamo> delete(Long id);

}
