package mx.edu.mx.prestamosYlibros.repository.prestamo;

import mx.edu.mx.prestamosYlibros.model.prestamo.EstadoPrestamo;
import mx.edu.mx.prestamosYlibros.model.prestamo.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {

    long countByLectorIdAndEstadoIn(Long lectorId, List<EstadoPrestamo>estados);

    List<Prestamo>findByLectorIdAndEstadoIn(Long lectorId, List<EstadoPrestamo> estados);

}
