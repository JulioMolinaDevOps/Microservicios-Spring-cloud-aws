package org.julio.springcloud.msvc.cursos.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "cursos")
public class Cursos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotNull
    @NotEmpty
    @Column(unique = true)
    private String nombre;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "curso_id")
    private List<CursoAlumno> cursosalumnos;

    @Transient
    private List<Alumno>alumnos;

    public Cursos() {

        cursosalumnos= new ArrayList<>();
        alumnos=new ArrayList<>();
    }

    public void agregarcursoAlumno(CursoAlumno cursoAlumno){
        cursosalumnos.add(cursoAlumno);
    }

    public void eliminarCursoAlumno(CursoAlumno cursoAlumno){
        cursosalumnos.remove(cursoAlumno);
    }

    public List<CursoAlumno> getCursosalumnos() {
        return cursosalumnos;
    }

    public void setCursosalumnos(List<CursoAlumno> cursosalumnos) {
        this.cursosalumnos = cursosalumnos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Alumno> getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(List<Alumno> alumnos) {
        this.alumnos = alumnos;
    }
}
