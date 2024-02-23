package proyecto_spring.services;

import proyecto_spring.entities.Actividad;
import proyecto_spring.entities.Libro;

import java.util.List;
import java.util.Optional;

public interface ActividadService {

    List<Actividad> findAll();
    Optional<Actividad> findById(Long id);
    Actividad save (Actividad actividad);
    Optional <Actividad> update(Long id, Actividad actividad);
    Optional<Actividad> delete(Long id);


}
