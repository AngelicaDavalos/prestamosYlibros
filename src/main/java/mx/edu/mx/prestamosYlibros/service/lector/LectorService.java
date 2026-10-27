package mx.edu.mx.prestamosYlibros.service.lector;


import mx.edu.mx.prestamosYlibros.dto.LectorResponseDto;
import mx.edu.mx.prestamosYlibros.exeption.customExceptions.CustomNotFoundException;
import mx.edu.mx.prestamosYlibros.model.lector.Lector;
import mx.edu.mx.prestamosYlibros.repository.lector.LectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LectorService {

    @Autowired
    private LectorRepository lectorRepository;

    // 4. rehabilitarLector -> ⭐ 10 pts
    public LectorResponseDto rehabilitarLector(Long id) {
        Lector lector = lectorRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("El lector no existe"));

        lector.setActivo(true);
        Lector guardado = lectorRepository.save(lector);

        return new LectorResponseDto(
                guardado.getId(),
                guardado.getNombre(),
                guardado.isActivo(),
                guardado.isMembresiaPremium()
        );
    }

}
