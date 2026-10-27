package mx.edu.mx.prestamosYlibros.repository.lector;

import mx.edu.mx.prestamosYlibros.model.lector.Lector;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectorRepository extends JpaRepository<Lector, Long> {
}
