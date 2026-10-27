package mx.edu.mx.prestamosYlibros.repository.libro;

import mx.edu.mx.prestamosYlibros.model.libro.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibroRepository extends JpaRepository<Libro, Long> {
}
