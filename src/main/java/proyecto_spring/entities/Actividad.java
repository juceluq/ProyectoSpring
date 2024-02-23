package proyecto_spring.entities;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="actividades")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Actividad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="usuario_id", referencedColumnName = "id")
    @NotNull(message = "El campo usuario no puede ser nulo")
    private User usuario;

    @Column(name = "fecha_hora_actividad")
    @JsonFormat(pattern="dd/MM/yyyy HH:mm")
    private LocalDateTime fechaHoraActividad;

    private String descripcion;

    @PrePersist
    protected void onCreate() {
        fechaHoraActividad = LocalDateTime.now();
    }
}
