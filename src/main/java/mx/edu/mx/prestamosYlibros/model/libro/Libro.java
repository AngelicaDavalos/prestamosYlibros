package mx.edu.mx.prestamosYlibros.model.libro;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "libros")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "el titulo no puede ser nulo")
    private  String titulo;

    @NotNull(message = "Los ejemplares disponibles no puededn ser nulos ")
    private int ejemplaresDisponibles;
}
