package proyecto_spring.services;

import proyecto_spring.entities.Libro;

import java.util.List;
import java.util.Optional;

public interface LibroService {

    List<Libro> findAll();
    Optional<Libro> findById(Long id);
    Libro save (Libro libro);
    Optional <Libro> update(Long id, Libro libro);
    Optional<Libro> delete(Long id);

}
