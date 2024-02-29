package proyecto_spring.entities;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name="reservas")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reserva {

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
    @NotNull(message = "El campo fechaReserva no puede ser nulo (dd/MM/yyyy)")
    @JsonFormat(pattern="dd/MM/yyyy")
    private LocalDate fechaReserva;

    public enum EstadoReserva {
        RESERVADO,
        CANCELADO,
        DISPONIBLE
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    @NotNull(message = "El campo estado no puede ser nulo(Reservado, Cancelado, Disponible)")
    private EstadoReserva estado;

    @JsonProperty("estado")
    public void setEstado(String estado) {
        this.estado = EstadoReserva.valueOf(estado.toUpperCase());
    }


    @AssertTrue(message="La fecha de reserva debe ser posterior a la fecha de hoy.")
    private boolean isFechaReservaValida() {
        LocalDate fechaInicial = LocalDate.now();
        if(fechaReserva == null){
            return true;
        }else{
            return fechaReserva.isAfter(fechaInicial);
        }
    }
}
