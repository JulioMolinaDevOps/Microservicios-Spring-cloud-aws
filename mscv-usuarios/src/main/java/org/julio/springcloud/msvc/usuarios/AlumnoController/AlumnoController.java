package org.julio.springcloud.msvc.usuarios.AlumnoController;

import jakarta.validation.Valid;
import org.jspecify.annotations.NonNull;
import org.julio.springcloud.msvc.usuarios.ServiceAlumno.AlumnoService;
import org.julio.springcloud.msvc.usuarios.model.entity.Alumno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.naming.Binding;
import java.util.*;

@RestController
public class AlumnoController {

    @Autowired
    private AlumnoService alumnoservice;



    @GetMapping
    public ResponseEntity<List<Alumno>>listarAlumno(){

        return ResponseEntity.ok(alumnoservice.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity <?> listarporId(@PathVariable Long id){
         Optional<Alumno> optionalAlumno = alumnoservice.listarporId(id);
         if(optionalAlumno.isPresent()){
             return ResponseEntity.ok().body(optionalAlumno.get());
         }else{
             return ResponseEntity.notFound().build();
         }

    }


    @PostMapping
    public ResponseEntity<?> guardarAlumno(@Valid @RequestBody Alumno alumno, BindingResult result){

        if(result.hasErrors()){
            return validar(result);
        }

        if(!alumno.getCorreo().isEmpty() && alumnoservice.porCorreo(alumno.getCorreo()).isPresent()){
            return  ResponseEntity
                    .badRequest()
                    .body(Collections.singletonMap("mensaje","ya existe un usuario con ese correo electronico"));
        }

        return  ResponseEntity.status(HttpStatus.CREATED).body(alumnoservice.guardar(alumno));
    }



    @PutMapping("/{id}")
    public ResponseEntity<?>editar(@Valid @RequestBody Alumno alumno,BindingResult result, @PathVariable Long id){

        if(result.hasErrors()){
            return validar(result);
        }

        Optional<Alumno> optionalAlumno = alumnoservice.listarporId(id);
        if (optionalAlumno.isPresent()){
            Alumno alumnoDb= optionalAlumno.get();
            if(!alumno.getCorreo().isEmpty() && !alumno.getCorreo().equalsIgnoreCase(alumnoDb.getCorreo()) && alumnoservice.porCorreo(alumno.getCorreo()).isPresent()){
                return  ResponseEntity
                        .badRequest()
                        .body(Collections.singletonMap("mensaje","ya existe un usuario con ese correo electronico"));
            }


            alumnoDb.setNombre(alumno.getNombre());
            alumnoDb.setCorreo(alumno.getCorreo());
            alumnoDb.setContrasena(alumno.getContrasena());
            return ResponseEntity.status(HttpStatus.CREATED).body(alumnoservice.guardar(alumnoDb));

        }
        return ResponseEntity.notFound().build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarAlumno(@PathVariable  Long id){
        Optional<Alumno> optionalAlumno= alumnoservice.listarporId(id);
        if(optionalAlumno.isPresent()){
            alumnoservice.eliminar(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();

    }

    @GetMapping("/alumnoporcursos")
    public ResponseEntity<?> obtenerAlumnoporCurso(@RequestParam List<Long> ids){
        return ResponseEntity.ok(alumnoservice.getAlliDalumnos(ids));
    }


    private ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String,String> errores= new HashMap<>();
        result.getFieldErrors().forEach(err->{
            errores.put(err.getField(), "el campo : " +err.getField()+ " " +err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }

}
