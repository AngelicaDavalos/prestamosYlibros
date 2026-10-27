package mx.edu.mx.prestamosYlibros.controller;


import jakarta.validation.Valid;
import mx.edu.mx.prestamosYlibros.dto.PrestamoResponseDto;
import mx.edu.mx.prestamosYlibros.dto.SolicitarPrestamoDTO;
import mx.edu.mx.prestamosYlibros.service.prestamo.PrestamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {

    @Autowired
    private PrestamoService prestamoService;

    // olicitarPrestamo
    @PostMapping("/solicitar")
    public ResponseEntity<PrestamoResponseDto> solicitarPrestamo(@Valid @RequestBody SolicitarPrestamoDTO dto) {
        return new ResponseEntity<>(prestamoService.solicitarPrestamo(dto), HttpStatus.CREATED);
    }

    // devolverLibro
    @PatchMapping("/{id}/devolver")
    public ResponseEntity<PrestamoResponseDto> devolverLibro(@PathVariable Long id) {
        return ResponseEntity.ok(prestamoService.devolverLibro(id));
    }

    // renovarPrestam
    @PatchMapping("/{id}/renovar")
    public ResponseEntity<PrestamoResponseDto> renovarPrestamo(@PathVariable Long id) {
        return ResponseEntity.ok(prestamoService.renovarPrestamo(id));
    }

    // listarPrestamosActivosLector
    @GetMapping("/lector/{lectorId}")
    public ResponseEntity<List<PrestamoResponseDto>> listarPrestamosActivosLector(@PathVariable Long lectorId) {
        return ResponseEntity.ok(prestamoService.listarPrestamosActivosLector(lectorId));
    }

}
