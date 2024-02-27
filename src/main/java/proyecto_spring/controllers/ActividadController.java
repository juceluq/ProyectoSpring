package proyecto_spring.controllers;


import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import proyecto_spring.entities.Actividad;
import proyecto_spring.services.ActividadService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/actividades")
public class ActividadController {

    @Autowired
    private ActividadService actividadService;

    @GetMapping
    @Operation(summary = "Obtiene una lista de todas las actividades.")
    public List<Actividad> list(){
        return actividadService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene una actividad en especifico.")
    public ResponseEntity<Actividad> view(@PathVariable Long id){
        Optional<Actividad> actividadOptional = actividadService.findById(id);
        if(actividadOptional.isPresent()){
            return ResponseEntity.ok(actividadOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Crea una actividad de un usuario.")
    public ResponseEntity<Actividad> create(@RequestBody @Validated Actividad actividad) {
        Actividad savedActividad = actividadService.save(actividad);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedActividad);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza la información de una actividad.")
    public ResponseEntity<Actividad> update(@PathVariable Long id, @RequestBody @Validated Actividad actividad){
        Optional <Actividad> actividadOptional = actividadService.update(id, actividad);
        if(actividadOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(actividadOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Borra una actividad.")
    public ResponseEntity<Actividad> delete(@PathVariable Long id){
        Optional<Actividad> actividadOptional = actividadService.delete(id);
        if(actividadOptional.isPresent()){
            return ResponseEntity.ok(actividadOptional.orElseThrow());
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
