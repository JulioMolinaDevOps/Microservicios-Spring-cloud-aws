package org.julio.springcloud.msvc.usuarios.repository;

import org.julio.springcloud.msvc.usuarios.model.entity.Alumno;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AlumnoRepository extends CrudRepository<Alumno, Long> {

    Optional<Alumno> findByCorreo(String correo);


    //consultar con Query personalizada
   /* @Query("select a from Alumno a where a.correo=?1")
    Optional<Alumno>findporCorreo(String correo);*/
}
