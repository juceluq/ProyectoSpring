package proyecto_spring.controllers;

import proyecto_spring.entities.Libro;
import proyecto_spring.services.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/libros")
public class LibroController {

    @Autowired
    private LibroService libroService;

    @GetMapping
    public List<Libro> list(){
        return libroService.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Libro> view(@PathVariable Long id){
        Optional<Libro> productOptional = libroService.findById(id);
        if(productOptional.isPresent()){
            return ResponseEntity.ok(productOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Libro> create(@RequestBody Libro libro){
        return ResponseEntity.status(HttpStatus.CREATED).body(libroService.save(libro));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Libro> update(@PathVariable Long id, @RequestBody Libro libro){
        Optional <Libro> productOptional = libroService.update(id, libro);
        if(productOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(productOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Libro> delete(@PathVariable Long id){
        Optional<Libro> productOptional = libroService.delete(id);
        if(productOptional.isPresent()){
            return ResponseEntity.ok(productOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }
}
