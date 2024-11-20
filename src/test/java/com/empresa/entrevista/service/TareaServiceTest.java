package com.empresa.entrevista.service;

import com.empresa.entrevista.dao.EstadoTareaRepository;
import com.empresa.entrevista.dao.TareaRepository;
import com.empresa.entrevista.dao.UsuarioRepository;
import com.empresa.entrevista.dto.TareaDto;
import com.empresa.entrevista.entity.tarea.EstadoTarea;
import com.empresa.entrevista.entity.tarea.Tarea;
import com.empresa.entrevista.entity.usuario.Usuario;
import com.empresa.entrevista.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.any;

@SpringBootTest
public class TareaServiceTest {

    @Mock
    private TareaRepository tareaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private EstadoTareaRepository estadoTareaRepository;

    @InjectMocks
    private TareaService tareaService;

    @Test
    void testObtenerTodasLasTareas() {
        List<Tarea> mockTareas = new ArrayList<>();
        mockTareas.add(new Tarea(1L, "Tarea 1", "Descripción 1", new Usuario(), new EstadoTarea()));
        mockTareas.add(new Tarea(2L, "Tarea 2", "Descripción 2", new Usuario(), new EstadoTarea()));

        when(tareaRepository.findAll()).thenReturn(mockTareas);

        List<TareaDto> result = tareaService.obtenerTodasLasTareas();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void testCrearTarea() {
        TareaDto tareaDto = new TareaDto(1L, "Tarea 1", "Descripción 1", 1L, new EstadoTarea());

        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.of(new Usuario()));
        when(estadoTareaRepository.findById(anyLong())).thenReturn(Optional.of(new EstadoTarea()));
        when(tareaRepository.save(any(Tarea.class))).thenReturn(new Tarea());

        TareaDto result = tareaService.crearTarea(tareaDto);

        assertNotNull(result);
        assertEquals(tareaDto.titulo(), result.titulo());
        assertEquals(tareaDto.descripcion(), result.descripcion());
    }

    @Test
    void testGetTareaById() {
        Long taskId = 1L;
        when(tareaRepository.findById(taskId)).thenReturn(Optional.of(new Tarea(taskId, "Tarea 1", "Descripción 1", new Usuario(), new EstadoTarea())));

        TareaDto result = tareaService.getTareaById(taskId);

        assertNotNull(result);
        assertEquals(taskId, result.id());
    }

    @Test
    void testDeleteTarea() {
        Long taskId = 1L;
        when(tareaRepository.findById(taskId)).thenReturn(Optional.of(new Tarea()));

        assertDoesNotThrow(() -> tareaService.deleteTarea(taskId));
        verify(tareaRepository, times(1)).delete(any(Tarea.class));
    }

    @Test
    void testDeleteTarea_ResourceNotFoundException() {
        Long taskId = 1L;
        when(tareaRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> tareaService.deleteTarea(taskId));
        verify(tareaRepository, never()).delete(any(Tarea.class));
    }
}
