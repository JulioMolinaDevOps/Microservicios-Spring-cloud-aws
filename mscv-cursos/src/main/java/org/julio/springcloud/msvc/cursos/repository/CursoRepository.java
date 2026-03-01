package org.julio.springcloud.msvc.cursos.repository;

import org.julio.springcloud.msvc.cursos.entity.Cursos;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CursoRepository extends CrudRepository<Cursos,Long> {

    @Modifying
    @Query("delete from CursoAlumno cu where cu.alumno_id=?1")
    void eliminarcursoAlumnoporId(Long id);
}
