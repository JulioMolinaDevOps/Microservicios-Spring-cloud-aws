package org.julio.springcloud.msvc.cursos.clients;

import java.util.List;

import org.julio.springcloud.msvc.cursos.entity.Alumno;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "mscv-usuarios", url = "${mscv-usuarios.url}")
public interface AlumnoClientRest {

    @GetMapping("/{id}")
    Alumno listarporId(@PathVariable Long id);

    @PostMapping("/")
    Alumno guardarAlumno(@RequestBody Alumno alumno);

    @GetMapping("/alumnoporcursos")
    List<Alumno> obtenerAlumnoporCurso(@RequestParam Iterable<Long> ids);
}
