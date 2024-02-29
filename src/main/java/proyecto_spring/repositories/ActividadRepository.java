package proyecto_spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import proyecto_spring.entities.Actividad;

import java.time.LocalDateTime;
import java.util.List;

public interface ActividadRepository extends JpaRepository<Actividad, Long> {

    List<Actividad> findByFechaHoraActividadBetween(LocalDateTime inicio, LocalDateTime fin);
    List<Actividad> findByDescripcionContaining(String descripcion);
    List<Actividad> findByUsuarioId(Long usuarioId);
    @Query("SELECT a FROM Actividad a WHERE a.usuario.id = :usuarioId AND a.fechaHoraActividad BETWEEN :inicio AND :fin")
    List<Actividad> buscarActividadesPorUsuarioYFecha(
            @Param("usuarioId") Long usuarioId,
            @Param("inicio") LocalDateTime inicio,
            @Param("fin") LocalDateTime fin);
}
