package org.julio.springcloud.msvc.usuarios.ServiceAlumno;

import org.julio.springcloud.msvc.usuarios.clients.CursoClienteFeing;
import org.julio.springcloud.msvc.usuarios.model.entity.Alumno;
import org.julio.springcloud.msvc.usuarios.repository.AlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceAlumnoImple implements AlumnoService {

    @Autowired
    private AlumnoRepository alumnoRepo;

    @Autowired
    private CursoClienteFeing clienteFeing;

    @Override
    @Transactional(readOnly = true)
    public List<Alumno> listar() {
        return (List<Alumno>) alumnoRepo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Alumno> listarporId(Long id) {
        return alumnoRepo.findById(id);
    }

    @Override
    @Transactional
    public Alumno guardar(Alumno alumno) {
        return alumnoRepo.save(alumno);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        alumnoRepo.deleteById(id);
        clienteFeing.eliminarcursoalumno(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Alumno> getAlliDalumnos(Iterable<Long> ids) {
        return (List<Alumno>) alumnoRepo.findAllById(ids);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Alumno> porCorreo(String correo) {
        return alumnoRepo.findByCorreo(correo);
    }
}
