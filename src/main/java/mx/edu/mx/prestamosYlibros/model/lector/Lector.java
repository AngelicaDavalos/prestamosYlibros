package mx.edu.mx.prestamosYlibros.model.lector;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "lectores")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Lector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "el nombre no debe ir vacio")
    private String nombre;

    @NotNull(message = "El estado no puede ser nulo ")
    private boolean activo=true;

    @NotNull(message = "la menbresia no puede ser nula ")
    private boolean membresiaPremium=false;

}
