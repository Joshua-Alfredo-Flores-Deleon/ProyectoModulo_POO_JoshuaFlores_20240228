package JoshuaAlfredoFloresDeleon_20240228.JoshuaAlfredoFloresDeleon_20240228.Controllers;

import JoshuaAlfredoFloresDeleon_20240228.JoshuaAlfredoFloresDeleon_20240228.Models.DTO.LibrosDTO;
import JoshuaAlfredoFloresDeleon_20240228.JoshuaAlfredoFloresDeleon_20240228.Services.LibrosServices;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/apiLibros")
public class LibrosController {

    @Autowired
    private LibrosServices acceso;

    @GetMapping("/verLibros")
    public List<LibrosDTO> datosLibros() {
        return acceso.getAllLibros();
    }

    @PostMapping("/ingresarLibro")
    public ResponseEntity<Map<String, Object>> registrarUsuario(@Valid @RequestBody LibrosDTO libros, HttpServletRequest request) {
        try {
            //Intento de guardar el usuario
            LibrosDTO respuesta = acceso.insertLibro(libros);
            if (respuesta == null) {
                return ResponseEntity.badRequest().body(Map.of(
                        "status", "Inserción incorrecta",
                        "errorType", "VALIDATION_ERROR",
                        "message", "Datos del usuario inválidos"
                ));
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "status", "sucess",
                    "data", respuesta));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "status", "error",
                            "message", "error al registrar libro",
                            "detail", e.getMessage()
                    ));
        }
    }

    @PutMapping("/editarKibro/{id}")
    public ResponseEntity<?> editarLibro(
            @PathVariable Long id,
            @Valid @RequestBody LibrosDTO libro,
            BindingResult bindingResult){
            if (bindingResult.hasErrors()){
                Map<String, String> errores = new HashMap<>();
                bindingResult.getFieldErrors().forEach(error ->
                        errores.put(error.getField(), error.getDefaultMessage()));
                return ResponseEntity.badRequest().body(errores);
            }

            try{
                LibrosDTO libroActualizado = acceso.actualizaLibro(id, usuario);
                return ResponseEntity.ok(libroActualizado);
            }

            catch (ExcepcionDatosDuplicados e){
                return ResponseEntity.notFound().build();
            }

            catch (ExcepcionDatosDuplicados e){
                return ResponseEntity.status(HttpStatus.CONFLICT).body(
                        Map.of("error", "Datos duplicados", "campo", e.getCampoDuplicado)
                );
            }
    }

    @DeleteMapping("/eliminarLibro/{id}")
    public ResponseEntity<Map<String, Object>> eliminarLibro(@PathVariable Long id){
        try{
            if (!acceso.removerLibro(id)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .header("x-mensaje-Error", "Libro no encontrado")
                        .body(Map.of(
                                "error", "Not founbd",
                                "mensaje", "El usuario no ha sido encontrado",
                                "timestamp", Instant.now().toString()
                        ));
            }
            return ResponseEntity.ok().body(Map.of(
                "status", "proceso completado",
                "message", "usuario eliminado exitosamente"
            ));
        } catch (Exception e){
            return ResponseEntity.internalServerError().body(Map.of(
                "status", "error",
                "message", "error al eliminar el usuario",
                "detail", e.getMessage()
            ));
        }
    }
}