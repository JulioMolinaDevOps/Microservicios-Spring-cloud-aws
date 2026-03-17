package org.julio.springcloud.msvc.usuarios.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "mscv-cursos", url = "${mscv-cursos.url}")

public interface CursoClienteFeing {
    @DeleteMapping("/eliminarcursoalumno/{id}")
    void eliminarcursoalumno(@PathVariable Long id);
}
