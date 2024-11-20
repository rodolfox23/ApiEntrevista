package com.empresa.entrevista.service;

import com.empresa.entrevista.dao.EstadoTareaRepository;
import com.empresa.entrevista.dao.TareaRepository;
import com.empresa.entrevista.dao.UsuarioRepository;
import com.empresa.entrevista.dto.TareaDto;
import com.empresa.entrevista.entity.tarea.EstadoTarea;
import com.empresa.entrevista.entity.tarea.Tarea;
import com.empresa.entrevista.entity.usuario.Usuario;
import com.empresa.entrevista.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.empresa.entrevista.utils.Constantes.ESTADO_NO_ENCONTRADA;
import static com.empresa.entrevista.utils.Constantes.TAREA_NO_ENCONTRADA;
import static com.empresa.entrevista.utils.Constantes.USUARIO_NO_ENCONTRADO;

@Service
public class TareaService {

    private final TareaRepository tareaRepository;
    private final UsuarioRepository usuarioRepository ;
    private final EstadoTareaRepository estadoTareaRepository;

    public TareaService(TareaRepository tareaRepository,
                        UsuarioRepository usuarioRepository,
                        EstadoTareaRepository estadoTareaRepository) {
        this.tareaRepository        = tareaRepository;
        this.usuarioRepository      = usuarioRepository;
        this.estadoTareaRepository  = estadoTareaRepository;
    }
    public List<TareaDto> obtenerTodasLasTareas() {
        return tareaRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    public TareaDto crearTarea(TareaDto tareaDto) {
        Tarea tarea = new Tarea();
        tarea.setTitulo(tareaDto.titulo());
        tarea.setDescripcion(tareaDto.descripcion());
        Optional<EstadoTarea> estadoById = estadoTareaRepository.findById(1L);
        estadoById.ifPresent(tarea::setEstado);
        Usuario user = usuarioRepository.findById(tareaDto.userId())
                .orElseThrow(() -> new ResourceNotFoundException(USUARIO_NO_ENCONTRADO));
        tarea.setUsuario(user);
        tareaRepository.save(tarea);
        return convertToDto(tarea);
    }

    public TareaDto getTareaById(Long id) {
        Tarea task = tareaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TAREA_NO_ENCONTRADA));
        return convertToDto(task);
    }

    public TareaDto updateTask(Long id,
                               TareaDto tareaDto) {
        Tarea tarea = tareaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TAREA_NO_ENCONTRADA));

        tarea.setTitulo(tareaDto.titulo());
        tarea.setDescripcion(tareaDto.descripcion());
        Optional<EstadoTarea> estado = Optional.ofNullable(estadoTareaRepository.findById(tareaDto.estado().getId())
                .orElseThrow(() -> new ResourceNotFoundException(ESTADO_NO_ENCONTRADA)));
        estado.ifPresent(tarea::setEstado);
        tareaRepository.save(tarea);
        return convertToDto(tarea);
    }

    public void deleteTarea(Long id) {
        Tarea task = tareaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TAREA_NO_ENCONTRADA));
        tareaRepository.delete(task);
    }

    private TareaDto convertToDto(Tarea task) {
        return new TareaDto(
                task.getId(),
                task.getTitulo(),
                task.getDescripcion(),
                task.getUsuario().getUser_id(),
                task.getEstado()
        );
    }
}
