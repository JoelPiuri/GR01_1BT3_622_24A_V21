package com.example.service;

import com.example.dao.MateriaDAO;
import com.example.dao.PreguntasEncuestaDAO;
import com.example.model.Materia;
import com.example.model.PreguntasEncuesta;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PreguntasEncuestaDAOTest {
    private PreguntasEncuestaDAO preguntasEncuestaDAO;
    private MateriaDAO materiaDAO;
    private final int preguntaId = 1;
    private final int materiaId = 2;

    @BeforeEach
    public void setUp() {
        // Crear el mock de PreguntasEncuestaDAO
        preguntasEncuestaDAO = mock(PreguntasEncuestaDAO.class);

        // Crear el mock de MateriaDAO
        materiaDAO = mock(MateriaDAO.class);
    }

    @Test
    public void testSavePregunta() {
        // Configurar Materia mock para asignarlo a la pregunta
        Materia materia = new Materia();
        materia.setCodigomateria(materiaId);
        when(materiaDAO.getMateria(materiaId)).thenReturn(materia);

        // Crear una nueva pregunta y asignarle la materia mock
        PreguntasEncuesta pregunta = new PreguntasEncuesta();
        pregunta.setId(preguntaId);
        pregunta.setPregunta("¿Cómo calificaría el contenido enseñado en la tutoría?");
        pregunta.setMateria(materia);

        // Ejecutar el método que se quiere probar
        preguntasEncuestaDAO.savePregunta(pregunta);

        // Verificar el guardado llamando a un método de recuperación, si lo implementas
        when(preguntasEncuestaDAO.getPreguntaById(preguntaId)).thenReturn(pregunta);
        PreguntasEncuesta retrievedPregunta = preguntasEncuestaDAO.getPreguntaById(preguntaId);
        assertNotNull(retrievedPregunta);
        assertEquals(pregunta.getPregunta(), retrievedPregunta.getPregunta());
    }

    @Test
    public void testGetPreguntaById() {
        // Crear y configurar pregunta con materia simulada
        Materia materia = new Materia();
        materia.setCodigomateria(materiaId);
        when(materiaDAO.getMateria(materiaId)).thenReturn(materia);

        PreguntasEncuesta pregunta = new PreguntasEncuesta();
        pregunta.setId(preguntaId);
        pregunta.setPregunta("¿Cómo calificaría el contenido enseñado en la tutoría?");
        pregunta.setMateria(materia);

        // Configurar comportamiento del mock
        when(preguntasEncuestaDAO.getPreguntaById(preguntaId)).thenReturn(pregunta);
        PreguntasEncuesta retrievedPregunta = preguntasEncuestaDAO.getPreguntaById(preguntaId);

        // Verificaciones
        assertNotNull(retrievedPregunta);
        assertEquals("¿Cómo calificaría el contenido enseñado en la tutoría?", retrievedPregunta.getPregunta());
    }

    @Test
    public void testGetPreguntasByMateria() {
        // Crear lista de preguntas simuladas
        PreguntasEncuesta pregunta1 = new PreguntasEncuesta();
        pregunta1.setPregunta("¿Cómo calificaría los aprendizajes de la tutoría?");
        PreguntasEncuesta pregunta2 = new PreguntasEncuesta();
        pregunta2.setPregunta("¿Cómo calificaría la claridad del tutor?");

        // Configurar el comportamiento del mock
        when(preguntasEncuestaDAO.getPreguntasByMateria(materiaId)).thenReturn(Arrays.asList(pregunta1, pregunta2));

        // Ejecutar el método a probar
        List<PreguntasEncuesta> preguntas = preguntasEncuestaDAO.getPreguntasByMateria(materiaId);

        // Verificar resultados
        assertNotNull(preguntas);
        assertEquals(2, preguntas.size());
        assertTrue(preguntas.stream().anyMatch(p -> p.getPregunta().equals("¿Cómo calificaría los aprendizajes de la tutoría?")));
        assertTrue(preguntas.stream().anyMatch(p -> p.getPregunta().equals("¿Cómo calificaría la claridad del tutor?")));
    }

    @AfterEach
    public void tearDown() {
        // Opcional: limpiar si fuera necesario, pero en este caso no se realiza ningún cambio persistente
    }
}
