package proyecto_spring.controllers;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import proyecto_spring.entities.Libro;
import proyecto_spring.services.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/libros")
public class LibroController {

    @Autowired
    private LibroService libroService;

    @GetMapping
    @Operation(summary = "Obtiene una lista de libros.")
    public List<Libro> list(){
        return libroService.findAll();
    }
    @GetMapping("/{id}")
    @Operation(summary = "Obtiene un libro.")
    public ResponseEntity<Libro> view(@PathVariable Long id){
        Optional<Libro> productOptional = libroService.findById(id);
        if(productOptional.isPresent()){
            return ResponseEntity.ok(productOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Crea un libro.")
    public ResponseEntity<Libro> create(@RequestBody Libro libro){
        return ResponseEntity.status(HttpStatus.CREATED).body(libroService.save(libro));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza un libro.")
    public ResponseEntity<Libro> update(@PathVariable Long id, @RequestBody Libro libro){
        Optional <Libro> productOptional = libroService.update(id, libro);
        if(productOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(productOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Borra un libro.")
    public ResponseEntity<Libro> delete(@PathVariable Long id){
        Optional<Libro> productOptional = libroService.delete(id);
        if(productOptional.isPresent()){
            return ResponseEntity.ok(productOptional.orElseThrow());
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
