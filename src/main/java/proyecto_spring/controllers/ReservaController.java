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
    @Operation(summary = "Obtiene una lista de reservas.")
    @ApiResponse(responseCode = "200", description = "Lista de reservas encontradas.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Reserva.class)))
    public List<Reserva> list(){
        return reservaService.findAll();
    }
    @GetMapping("/{id}")
    @Operation(summary = "Obtiene una reserva por su ID.")
    @ApiResponse(responseCode = "200", description = "Reserva encontrada.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Reserva.class)))
    @ApiResponse(responseCode = "404", description = "Reserva no encontrada.")
    public ResponseEntity<Reserva> view(@PathVariable Long id){
        Optional<Reserva> reservaOptional = reservaService.findById(id);
        if(reservaOptional.isPresent()){
            return ResponseEntity.ok(reservaOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Crea una nueva reserva.")
    @ApiResponse(responseCode = "201", description = "Reserva creada exitosamente.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Reserva.class)))
    @ApiResponse(responseCode = "400", description = "Error de validación en los datos de la reserva.", content = @Content(mediaType = "application/json"))
    public ResponseEntity<Reserva> create(@RequestBody @Validated Reserva reserva) {
        Reserva savedReserva = reservaService.save(reserva);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedReserva);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza una reserva existente.")
    @ApiResponse(responseCode = "200", description = "Reserva actualizada exitosamente.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Reserva.class)))
    @ApiResponse(responseCode = "404", description = "Reserva no encontrada para actualizar.")
    @ApiResponse(responseCode = "400", description = "Error de validación en los datos de la reserva.", content = @Content(mediaType = "application/json"))
    public ResponseEntity<Reserva> update(@PathVariable Long id, @RequestBody @Validated Reserva reserva){
        Optional <Reserva> reservaOptional = reservaService.update(id, reserva);
        if(reservaOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(reservaOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina una reserva.")
    @ApiResponse(responseCode = "200", description = "Reserva eliminada exitosamente.", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "404", description = "Reserva no encontrada para eliminar.")
    public ResponseEntity<Reserva> delete(@PathVariable Long id){
        Optional<Reserva> reservaOptional = reservaService.delete(id);
        if(reservaOptional.isPresent()){
            return ResponseEntity.ok(reservaOptional.orElseThrow());
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
