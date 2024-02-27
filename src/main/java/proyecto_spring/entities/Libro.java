package proyecto_spring.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="libros")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El campo autor no puede ser nulo")
    private String autor;

    @NotNull(message = "El campo isbn no puede ser nulo")
    private Integer isbn;

    @NotNull(message = "El campo disponibilidad no puede ser nulo(true,false)")
    private boolean disponibilidad;

}
