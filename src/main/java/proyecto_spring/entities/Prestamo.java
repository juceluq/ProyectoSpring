package proyecto_spring.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;


@Entity
@Table(name="prestamos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="usuario_id", referencedColumnName = "id")
    @NotNull(message = "El campo usuario no puede ser nulo")
    private User usuario;

    @ManyToOne
    @JoinColumn(name="libro_id", referencedColumnName = "id")
    @NotNull(message = "El campo libro no puede ser nulo")
    private Libro libro;

    @Column(name = "fecha_reserva")
    @JsonFormat(pattern="dd/MM/yyyy")
    private LocalDate fechaReserva;

    @Column(name = "fecha_limite")
    @NotNull(message = "El campo fechaLimite no puede ser nulo (dd/MM/yyyy)")
    @JsonFormat(pattern="dd/MM/yyyy")
    private LocalDate fechaLimite;

    @Column(name = "devuelto")
    @NotNull(message = "El campo devuelto no puede ser nulo (true = si, false = no)")
    private boolean devuelto;

    @AssertTrue(message="La fecha límite debe ser posterior a la fecha de reserva")
    private boolean isFechaLimiteValida() {
        fechaReserva = LocalDate.now();
        return fechaLimite.isAfter(fechaReserva);
    }
}
