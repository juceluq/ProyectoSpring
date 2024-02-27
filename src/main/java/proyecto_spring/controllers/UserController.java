

package proyecto_spring.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import proyecto_spring.entities.User;
import proyecto_spring.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    @ApiResponse(
            responseCode = "200",
            description = "Ejemplo de usuario.",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema =
                            @Schema(implementation = User.class))})
    @Operation(summary = "Obtiene una lista de todos los usuarios existentes.")
    public List<User> list(){
        return userService.findAll();
    }

    @PostMapping
    @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)))
    @ApiResponse(responseCode = "400", description = "Error de validación en los datos del usuario.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)))
    @Operation(summary = "Crea un nuevo usuario.")
    public ResponseEntity<?> create(@Valid @RequestBody User user, BindingResult result){
        if(result.hasFieldErrors()){
            return validation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
    }

    @PostMapping("/register")
    @ApiResponse(responseCode = "201", description = "Usuario registrado exitosamente.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)))
    @ApiResponse(responseCode = "400", description = "Error de validación en los datos de registro del usuario.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)))
    @Operation(summary = "Registra un nuevo usuario.")
    public ResponseEntity<?> register(@Valid @RequestBody User user, BindingResult result){


        return create(user, result);
    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();

        result.getFieldErrors().forEach(err -> errors.put(err.getField(), "El campo "+ err.getField()+ " "+ err.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }
}
