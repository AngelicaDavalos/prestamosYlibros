package mx.edu.mx.prestamosYlibros.service.prestamo;

import jakarta.transaction.Transactional;
import mx.edu.mx.prestamosYlibros.dto.PrestamoResponseDto;
import mx.edu.mx.prestamosYlibros.dto.SolicitarPrestamoDTO;
import mx.edu.mx.prestamosYlibros.exeption.customExceptions.CustomBadException;
import mx.edu.mx.prestamosYlibros.exeption.customExceptions.CustomNotFoundException;
import mx.edu.mx.prestamosYlibros.model.lector.Lector;
import mx.edu.mx.prestamosYlibros.model.libro.Libro;
import mx.edu.mx.prestamosYlibros.model.prestamo.EstadoPrestamo;
import mx.edu.mx.prestamosYlibros.model.prestamo.Prestamo;
import mx.edu.mx.prestamosYlibros.repository.lector.LectorRepository;
import mx.edu.mx.prestamosYlibros.repository.libro.LibroRepository;
import mx.edu.mx.prestamosYlibros.repository.prestamo.PrestamoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class PrestamoService {

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private LectorRepository lectorRepository;

    @Autowired
    private LibroRepository libroRepository;



    // solicitar prestamso
    @Transactional
    public PrestamoResponseDto solicitarPrestamo(SolicitarPrestamoDTO dto) {
        Lector lector = lectorRepository.findById(dto.getLectorId())
                .orElseThrow(() -> new CustomNotFoundException("El lector no existe"));

        if (!lector.isActivo()) {
            throw new CustomBadException("El lector no esta activo");
        }

        Libro libro = libroRepository.findById(dto.getLibroId())
                .orElseThrow(() -> new CustomNotFoundException("El libro no existe"));

        if (libro.getEjemplaresDisponibles() < 1) {
            throw new CustomBadException("No hay ejemplares disponibles de este libro");
        }

        List<EstadoPrestamo> estadosActivos = Arrays.asList(EstadoPrestamo.PRESTADO, EstadoPrestamo.VENCIDO);
        long prestamosActivos = prestamoRepository.countByLectorIdAndEstadoIn(lector.getId(), estadosActivos);

        int limiteMaximo = lector.isMembresiaPremium() ? 5 : 3;
        if (prestamosActivos >= limiteMaximo) {
            throw new CustomBadException("El lector ha alcanzado el limite maximo de prestamos activos (" + limiteMaximo + ")");
        }

        // restar 1 al contador de ejemplares disponibles
        libro.setEjemplaresDisponibles(libro.getEjemplaresDisponibles() - 1);
        libroRepository.save(libro);

        // crear registro de Prestamo
        Prestamo prestamo = new Prestamo();
        prestamo.setLector(lector);
        prestamo.setLibro(libro);
        prestamo.setFechaPrestamo(LocalDate.now());
        prestamo.setFechaDevolucionMaxima(LocalDate.now().plusDays(14));
        prestamo.setEstado(EstadoPrestamo.PRESTADO);

        Prestamo guardado = prestamoRepository.save(prestamo);

        return convertirAPrestamoDto(guardado);
    }

    // devolverLibro
    public PrestamoResponseDto devolverLibro(Long id) {
        Prestamo prestamo = prestamoRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("El prestamo no existe"));

        if (prestamo.getEstado() == EstadoPrestamo.DEVUELTO) {
            throw new CustomBadException("El libro ya ha sido devuelto previamente");
        }

        // Si la fecha actual es posterior a la devolución máxima, estuvo vencido
        if (LocalDate.now().isAfter(prestamo.getFechaDevolucionMaxima())) {
            Lector lector = prestamo.getLector();
            lector.setActivo(false); // desactivar por entrega tardía
            lectorRepository.save(lector);
        }

        prestamo.setEstado(EstadoPrestamo.DEVUELTO);

        // sumar 1 al contador de ejemplares disponibles
        Libro libro = prestamo.getLibro();
        libro.setEjemplaresDisponibles(libro.getEjemplaresDisponibles() + 1);
        libroRepository.save(libro);

        Prestamo actualizado = prestamoRepository.save(prestamo);

        return convertirAPrestamoDto(actualizado);
    }

    // renovarPrestamo
    public PrestamoResponseDto renovarPrestamo(Long id) {
        Prestamo prestamo = prestamoRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("El prestamo no existe"));

        if (prestamo.getEstado() == EstadoPrestamo.VENCIDO || prestamo.getEstado() == EstadoPrestamo.DEVUELTO) {
            throw new CustomBadException("No se puede renovar un prestamo vencido o devuelto");
        }

        prestamo.setFechaDevolucionMaxima(prestamo.getFechaDevolucionMaxima().plusDays(7));
        Prestamo renovado = prestamoRepository.save(prestamo);

        return convertirAPrestamoDto(renovado);
    }

    // listarPrestamosActivosLector
    public List<PrestamoResponseDto> listarPrestamosActivosLector(Long lectorId) {
        if (!lectorRepository.existsById(lectorId)) {
            throw new CustomNotFoundException("El lector no existe");
        }

        List<EstadoPrestamo> estadosActivos = Arrays.asList(EstadoPrestamo.PRESTADO, EstadoPrestamo.VENCIDO);
        List<Prestamo> lista = prestamoRepository.findByLectorIdAndEstadoIn(lectorId, estadosActivos);
        List<PrestamoResponseDto> respuesta = new ArrayList<>();

        for (Prestamo p : lista) {
            respuesta.add(convertirAPrestamoDto(p));
        }

        return respuesta;
    }

    private PrestamoResponseDto convertirAPrestamoDto(Prestamo p) {
        return new PrestamoResponseDto(
                p.getId(),
                p.getLector().getId(),
                p.getLector().getNombre(),
                p.getLibro().getId(),
                p.getLibro().getTitulo(),
                p.getFechaPrestamo(),
                p.getFechaDevolucionMaxima(),
                p.getEstado()
        );
    }
}


