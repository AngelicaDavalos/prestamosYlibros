package mx.edu.mx.prestamosYlibros.model.prestamo;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.mx.prestamosYlibros.model.lector.Lector;
import mx.edu.mx.prestamosYlibros.model.libro.Libro;

import java.time.LocalDate;

@Entity
@Table(name = "prestamos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El lector no puede ser nulo")
    @ManyToOne
    @JoinColumn(name = "lector_id")
    private Lector lector;

    @NotNull(message = "El libro no puede ser nulo")
    @ManyToOne
    @JoinColumn(name = "libro_id")
    private Libro libro;

    @NotNull(message = "La fecha de prestamo no puede ser nula")
    private LocalDate fechaPrestamo;

    @NotNull(message = "La fecha de devolucion no puede ser nula")
    private LocalDate fechaDevolucionMaxima;

    @NotNull(message = "El estado del prestamo no puede ser nulo")
    @Enumerated(EnumType.STRING)
    private EstadoPrestamo estado;
}
