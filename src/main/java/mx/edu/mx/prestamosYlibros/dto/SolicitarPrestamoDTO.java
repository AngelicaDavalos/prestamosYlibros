package mx.edu.mx.prestamosYlibros.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SolicitarPrestamoDTO {

    @NotNull(message = "el id del lector no puede ser nulo ")
    private  Long lectorId;

    @NotNull(message = "El id del libro no puede ser nulo ")
    private Long libroId;

}
