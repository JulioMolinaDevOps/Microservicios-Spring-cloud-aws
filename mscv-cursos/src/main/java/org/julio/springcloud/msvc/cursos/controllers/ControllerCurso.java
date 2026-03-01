package org.julio.springcloud.msvc.cursos.controllers;

import feign.FeignException;
import jakarta.validation.Valid;

import org.julio.springcloud.msvc.cursos.entity.Alumno;
import org.julio.springcloud.msvc.cursos.entity.Cursos;
import org.julio.springcloud.msvc.cursos.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class ControllerCurso {

    @Autowired
    private CursoService cursoService;

    @GetMapping
    public ResponseEntity<List<Cursos>> listarCursos(){
        return ResponseEntity.ok(cursoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?>listarPorId(@PathVariable Long id){
        Optional<Cursos>cursofind=cursoService.obtenerAlumnoporCurso(id);
        if(cursofind.isPresent()){
            return ResponseEntity.ok().body(cursofind.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?>guardarCurso(@Valid @RequestBody Cursos curso, BindingResult result){
        if(result.hasErrors()){
            return validar(result);
        }
        return  ResponseEntity.status(HttpStatus.CREATED).body(cursoService.guardarCurso(curso));
    }


    @PutMapping("/{id}")
    public ResponseEntity<?>editarCurso(@Valid @RequestBody Cursos curso,BindingResult result, @PathVariable Long id){
        if(result.hasErrors()){
            return validar(result);
        }
        Optional<Cursos>findCursoid=cursoService.porId(id);
        if(findCursoid.isPresent()){
            Cursos cursoDb= findCursoid.get();
            cursoDb.setNombre(curso.getNombre());
            return ResponseEntity.status(HttpStatus.CREATED).body(cursoService.guardarCurso(cursoDb));
        }
        return  ResponseEntity.notFound().build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?>eliminarCurso(@PathVariable Long id){
        Optional<Cursos>findidCurso=cursoService.porId(id);
        if (findidCurso.isPresent()){
            cursoService.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    //comunicacion con el microservicio alumno
    @PutMapping("/asignaralumno/{cursoid}")
    public ResponseEntity<?> asignarAlumnoCurso(@Valid @RequestBody Alumno alumno, @PathVariable Long cursoid){
       Optional<Alumno> optionalAlumno=null;
              try {
                  optionalAlumno = cursoService.asignarCursoAlumno(alumno,cursoid);
              }catch (FeignException e){
                  return ResponseEntity.status(HttpStatus.NOT_FOUND)
                          .body(Collections
                                  .singletonMap("mensaje","No existe el alumno con ese id"+ " error en la comunicacion" + e.getMessage()));

              }
       if(optionalAlumno.isPresent()){
           return ResponseEntity.status(HttpStatus.CREATED).body(optionalAlumno.get());

       }

       return ResponseEntity.notFound().build();
    }

    @PostMapping("/crearalumno/{cursoid}")
    public ResponseEntity<?> crearalumnocurso(@Valid @RequestBody Alumno alumno, @PathVariable Long cursoid){
        Optional<Alumno> optionalAlumno=null;
        try {
            optionalAlumno = cursoService.crearAlumnoCurso(alumno,cursoid);
        }catch (FeignException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections
                            .singletonMap("mensaje","No se pudo crear el alumno con ese id" + " error en la comunicacion" + e.getMessage()));

        }
        if(optionalAlumno.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(optionalAlumno.get());

        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/quitar-alumno/{cursoid}")
    public ResponseEntity<?> quitarAlumnoCurso(@Valid @RequestBody Alumno alumno, @PathVariable Long cursoid){
        Optional<Alumno> optionalAlumno=null;
        try {
            optionalAlumno = cursoService.quitarAlumnoCurso(alumno,cursoid);
        }catch (FeignException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections
                            .singletonMap("mensaje","No existe el alumno con ese id"+ " error en la comunicacion" + e.getMessage()));

        }
        if(optionalAlumno.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(optionalAlumno.get());

        }

        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/eliminarcursoalumno/{id}")
    public ResponseEntity<?> eliminarcursoalumno(@PathVariable Long id){
        cursoService.eliminarcursoAlumnoporId(id);
        return ResponseEntity.noContent().build();
    }



    private ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String,String> errores= new HashMap<>();
        result.getFieldErrors().forEach(err->{
            errores.put(err.getField(), "el campo : " +err.getField()+ " " +err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }





}
