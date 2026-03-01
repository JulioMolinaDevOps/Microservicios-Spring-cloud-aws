package org.julio.springcloud.msvc.cursos.services;

import org.julio.springcloud.msvc.cursos.entity.Alumno;
import org.julio.springcloud.msvc.cursos.entity.Cursos;

import java.util.List;
import java.util.Optional;

public interface CursoService {
    List<Cursos> listar();
    Optional<Cursos> porId(Long id);
    Cursos guardarCurso(Cursos curso);
    void  eliminar(Long id);



    Optional<Alumno>asignarCursoAlumno(Alumno alumno,Long cursoid);
    Optional<Alumno>crearAlumnoCurso(Alumno alumno,Long cursoid);
    Optional<Alumno>quitarAlumnoCurso(Alumno alumno,Long cursoid);
    Optional<Cursos>obtenerAlumnoporCurso(Long id);
    void eliminarcursoAlumnoporId(Long id);
}
