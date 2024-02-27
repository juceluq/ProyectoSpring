package proyecto_spring.controllers;


import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import proyecto_spring.entities.Prestamo;
import proyecto_spring.services.PrestamoService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {

    @Autowired
    private PrestamoService prestamoService;

    @GetMapping
    @Operation(summary = "Obtiene una lista de préstamos.")
    public List<Prestamo> list(){
        return prestamoService.findAll();
    }
    @GetMapping("/{id}")
    @Operation(summary = "Obtiene un préstamo.")
    public ResponseEntity<Prestamo> view(@PathVariable Long id){
        Optional<Prestamo> prestamoOptional = prestamoService.findById(id);
        if(prestamoOptional.isPresent()){
            return ResponseEntity.ok(prestamoOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Crea un préstamo.")
    public ResponseEntity<Prestamo> create(@RequestBody @Validated Prestamo prestamo) {
        Prestamo savedPrestamo = prestamoService.save(prestamo);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPrestamo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza el préstamo.")
    public ResponseEntity<Prestamo> update(@PathVariable Long id, @RequestBody @Validated Prestamo prestamo){
        Optional <Prestamo> prestamoOptional = prestamoService.update(id, prestamo);
        if(prestamoOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(prestamoOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Borra un préstamo.")
    public ResponseEntity<Prestamo> delete(@PathVariable Long id){
        Optional<Prestamo> prestamoOptional = prestamoService.delete(id);
        if(prestamoOptional.isPresent()){
            return ResponseEntity.ok(prestamoOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
