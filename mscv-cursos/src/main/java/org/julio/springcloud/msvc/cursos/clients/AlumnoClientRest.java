package org.julio.springcloud.msvc.cursos.clients;

import jakarta.validation.Valid;
import org.julio.springcloud.msvc.cursos.entity.Alumno;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "mscv-usuarios",url = "host.docker.internal:8001")
public interface AlumnoClientRest {

    @GetMapping("/{id}")
    Alumno listarporId(@PathVariable Long id);
    @PostMapping("/")
    Alumno guardarAlumno(@RequestBody Alumno alumno);
    @GetMapping("/alumnoporcursos")
    List<Alumno> obtenerAlumnoporCurso(@RequestParam Iterable<Long> ids);
}
