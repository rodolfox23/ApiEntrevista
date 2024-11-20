package com.empresa.entrevista.dao;

import com.empresa.entrevista.entity.tarea.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TareaRepository extends JpaRepository<Tarea, Long> {
}
