package com.empresa.entrevista.dao;

import com.empresa.entrevista.entity.tarea.EstadoTarea;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstadoTareaRepository extends JpaRepository<EstadoTarea, Long> {
}
