package org.julio.springcloud.msvc.usuarios.ServiceAlumno;

import org.julio.springcloud.msvc.usuarios.model.entity.Alumno;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public interface AlumnoService {
    List<Alumno> listar();
    Optional<Alumno> listarporId(Long id);
    Alumno guardar(Alumno alumno);
    void  eliminar(Long id);
    List<Alumno>getAlliDalumnos(Iterable<Long> ids);

    //metodo personalizado
    Optional<Alumno>porCorreo(String correo);
}
