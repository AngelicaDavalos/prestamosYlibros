package mx.edu.mx.prestamosYlibros.controller;


import mx.edu.mx.prestamosYlibros.dto.LectorResponseDto;
import mx.edu.mx.prestamosYlibros.service.lector.LectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lectores")
public class LectorController {

    @Autowired
    private LectorService lectorService;

    // rehabilitarLector
    @PatchMapping("/{id}/rehabilitar")
    public ResponseEntity<LectorResponseDto> rehabilitarLector(@PathVariable Long id) {
        return ResponseEntity.ok(lectorService.rehabilitarLector(id));
    }

}
