package proyecto_spring.services;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import proyecto_spring.entities.Actividad;
import proyecto_spring.entities.Libro;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ActividadService {

    List<Actividad> findAll();
    Optional<Actividad> findById(Long id);
    Actividad save (Actividad actividad);
    Optional <Actividad> update(Long id, Actividad actividad);
    Optional<Actividad> delete(Long id);

    List<Actividad> findByFechaHoraActividadBetween(LocalDateTime inicio, LocalDateTime fin);
    List<Actividad> findByDescripcionContaining(String descripcion);
    List<Actividad> findByUsuarioId(Long usuarioId);
    List<Actividad> buscarActividadesPorUsuarioYFecha(
            @Param("usuarioId") Long usuarioId,
            @Param("inicio") LocalDateTime inicio,
            @Param("fin") LocalDateTime fin);

}
