package org.julio.springcloud.msvc.cursos.services;

import org.jspecify.annotations.NonNull;
import org.julio.springcloud.msvc.cursos.clients.AlumnoClientRest;
import org.julio.springcloud.msvc.cursos.entity.Alumno;
import org.julio.springcloud.msvc.cursos.entity.CursoAlumno;
import org.julio.springcloud.msvc.cursos.entity.Cursos;
import org.julio.springcloud.msvc.cursos.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CursoServiceImplement implements CursoService{

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private AlumnoClientRest alumnoClientRest;

    @Transactional(readOnly = true)
    @Override
    public List<Cursos> listar() {
        return (List<Cursos>) cursoRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Cursos> porId(Long id) {
        return cursoRepository.findById(id);
    }

    @Transactional
    @Override
    public Cursos guardarCurso(Cursos curso) {
        return cursoRepository.save(curso);
    }

    @Transactional
    @Override
    public void eliminar(Long id) {
        cursoRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Optional<Alumno> asignarCursoAlumno(Alumno alumno, Long cursoid) {
        //Busca en la base de datos de cursos si existe curso con ese id
        //y devuelve el objeto dentro del optional en este caso un objeto de Cursos
        Optional<Cursos>findalumnoid=cursoRepository.findById(cursoid);

        if (findalumnoid.isPresent()){
            //Busca el alumno en el microservicio alumno
            //Esto hace una llamada HTTP al microservicio de alumnos
            Alumno newAlumnomscv= alumnoClientRest.listarporId(alumno.getId());

            //se sabe existe, entonces se saca del Optional.
            //y devuelve el objeto dentro del optional en este caso un objeto de Cursos
            Cursos curso=findalumnoid.get();
            //Creacion de la relacion
            CursoAlumno cursoAlumno= new CursoAlumno();
            cursoAlumno.setAlumno_id(newAlumnomscv.getId());

            //Agregar la relación al curso
            curso.agregarcursoAlumno(cursoAlumno);
            //se guarda el curso ya con al relacion de alumno
            cursoRepository.save(curso);

            //se devuelve el alumno
            return Optional.of(newAlumnomscv);

        }

        return Optional.empty();


    }


    @Transactional
    @Override
    public Optional<Alumno> crearAlumnoCurso(Alumno alumno, Long cursoid) {
        //Busca en la base de datos de cursos si existe curso con ese id
        //y devuelve el objeto dentro del optional en este caso un objeto de Cursos
        Optional<Cursos>findalumnoid=cursoRepository.findById(cursoid);

        if (findalumnoid.isPresent()){
            //Busca el alumno en el microservicio alumno
            //Esto hace una llamada HTTP al microservicio de alumnos
            Alumno newAlumnomscv= alumnoClientRest.guardarAlumno(alumno);

            //se sabe existe, entonces se saca del Optional.
            //y devuelve el objeto dentro del optional en este caso un objeto de Cursos
            Cursos curso=findalumnoid.get();
            //Creacion de la relacion
            CursoAlumno cursoAlumno= new CursoAlumno();
            cursoAlumno.setAlumno_id(newAlumnomscv.getId());

            //Agregar la relación al curso
            curso.agregarcursoAlumno(cursoAlumno);
            //se guarda el curso ya con al relacion de alumno
            cursoRepository.save(curso);

            //se devuelve el alumno
            return Optional.of(newAlumnomscv);

        }

        return Optional.empty();
    }


    @Transactional
    @Override
    public Optional<Alumno> quitarAlumnoCurso(Alumno alumno, Long cursoid) {
        Optional<Cursos>findalumnoid=cursoRepository.findById(cursoid);

        if (findalumnoid.isPresent()){
            //Busca el alumno en el microservicio alumno
            //Esto hace una llamada HTTP al microservicio de alumnos
            Alumno newAlumnomscv= alumnoClientRest.listarporId(alumno.getId());

            //se sabe existe, entonces se saca del Optional.
            //y devuelve el objeto dentro del optional en este caso un objeto de Cursos
            Cursos curso=findalumnoid.get();
            //Creacion de la relacion
            CursoAlumno cursoAlumno= new CursoAlumno();
            cursoAlumno.setAlumno_id(newAlumnomscv.getId());

            //elimuna la relación al curso
            curso.eliminarCursoAlumno(cursoAlumno);
            //se guarda el curso ya con al relacion de alumno
            cursoRepository.save(curso);

            //se devuelve el alumno
            return Optional.of(newAlumnomscv);

        }

        return Optional.empty();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Cursos> obtenerAlumnoporCurso(Long id) {
       Optional<Cursos>findcurso= cursoRepository.findById(id);
       if(findcurso.isPresent()){
           Cursos curso = findcurso.get();
           if(!curso.getCursosalumnos().isEmpty()){
               List<Long> ids= curso.getCursosalumnos().stream().map(cu ->cu.getAlumno_id())
                       .toList();

               List<Alumno> alumnos = alumnoClientRest.obtenerAlumnoporCurso(ids);
               curso.setAlumnos(alumnos);
           }
           return Optional.of(curso);
       }
        return Optional.empty();
    }

    @Transactional
    @Override
    public void eliminarcursoAlumnoporId(Long id) {
        cursoRepository.eliminarcursoAlumnoporId(id);
    }


}
