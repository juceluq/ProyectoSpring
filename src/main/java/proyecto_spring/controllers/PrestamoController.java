package proyecto_spring.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proyecto_spring.entities.Prestamo;
import proyecto_spring.services.PrestamoService;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {

    @Autowired
    private PrestamoService prestamoService;

    @GetMapping
    public List<Prestamo> list(){
        return prestamoService.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Prestamo> view(@PathVariable Long id){
        Optional<Prestamo> prestamoOptional = prestamoService.findById(id);
        if(prestamoOptional.isPresent()){
            return ResponseEntity.ok(prestamoOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Prestamo> create(@RequestBody Prestamo prestamo){
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        LocalDateTime fechaLimite;
        String fechaFormateada = DateTimeFormatter.ofPattern("dd-MM-yyyy").format(zonedDateTime);
        String horaFormateada = DateTimeFormatter.ofPattern("HH:mm:ss").format(zonedDateTime);
        prestamo.setFechaReserva(fechaFormateada + " | " + horaFormateada);

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            fechaLimite = LocalDateTime.parse(prestamo.getFechaLimite(), formatter);
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body(null);
        }
        prestamo.setFechaLimite(fechaLimite.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(prestamoService.save(prestamo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Prestamo> update(@PathVariable Long id, @RequestBody Prestamo prestamo){
        Optional <Prestamo> prestamoOptional = prestamoService.update(id, prestamo);
        if(prestamoOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(prestamoOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Prestamo> delete(@PathVariable Long id){
        Optional<Prestamo> prestamoOptional = prestamoService.delete(id);
        if(prestamoOptional.isPresent()){
            return ResponseEntity.ok(prestamoOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }
}
