package org.julio.springcloud.msvc.cursos.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "cursoalumno")
public class CursoAlumno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @Column(name = "alumno_id",unique = true)
    private Long alumno_id;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAlumno_id() {
        return alumno_id;
    }

    public void setAlumno_id(Long alumno_id) {
        this.alumno_id = alumno_id;
    }


    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        }
        if(!(obj instanceof CursoAlumno)){
            return false;
        }
        CursoAlumno o =(CursoAlumno) obj;
        return this.alumno_id != null && this.alumno_id.equals(o.alumno_id);
    }
}
