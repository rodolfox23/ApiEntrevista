package com.empresa.entrevista.dto;


import com.empresa.entrevista.entity.tarea.EstadoTarea;

public record TareaDto(Long id,
                       String titulo,
                       String descripcion,
                       Long userId,
                       EstadoTarea estado) {
}
