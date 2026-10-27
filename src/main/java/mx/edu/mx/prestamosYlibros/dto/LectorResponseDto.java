package mx.edu.mx.prestamosYlibros.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LectorResponseDto{

    private Long id;
    private String nombre;
    private boolean activo;
    private  boolean membresiaPremium;

}
