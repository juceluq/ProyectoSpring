package proyecto_spring.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import proyecto_spring.entities.Reserva;
import proyecto_spring.services.ReservaService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {
    @Autowired
    private ReservaService reservaService;

    @GetMapping
    public List<Reserva> list(){
        return reservaService.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Reserva> view(@PathVariable Long id){
        Optional<Reserva> reservaOptional = reservaService.findById(id);
        if(reservaOptional.isPresent()){
            return ResponseEntity.ok(reservaOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Reserva> create(@RequestBody @Validated Reserva reserva) {
        Reserva savedReserva = reservaService.save(reserva);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedReserva);
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

    @PutMapping("/{id}")
    public ResponseEntity<Reserva> update(@PathVariable Long id, @RequestBody @Validated Reserva reserva){
        Optional <Reserva> reservaOptional = reservaService.update(id, reserva);
        if(reservaOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(reservaOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Reserva> delete(@PathVariable Long id){
        Optional<Reserva> reservaOptional = reservaService.delete(id);
        if(reservaOptional.isPresent()){
            return ResponseEntity.ok(reservaOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }
}
