package com.example.service;

import com.example.model.Materia;
import com.example.model.PreguntasEncuesta;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GuardarPreguntaServletTest {

    @Test
    void testCrearInstanciaPregunta() {
        // Crea una instancia de PreguntasEncuesta
        PreguntasEncuesta pregunta = new PreguntasEncuesta();

        // Verifica que la pregunta no sea null al crearse
        assertNotNull(pregunta);

        // Verifica que el texto de la pregunta esté vacío por defecto
        assertNull(pregunta.getPregunta());
    }

    @Test
    void testCrearInstanciaMateria() {
        // Crea una instancia de Materia
        Materia materia = new Materia();

        // Verifica que la materia no sea null al crearse
        assertNotNull(materia);

        // Verifica que el código de la materia esté en su valor inicial (0)
        assertEquals(0, materia.getCodigomateria());
    }

    @Test
    void testAsignacionPregunta() {
        // Crea una instancia de PreguntasEncuesta
        PreguntasEncuesta pregunta = new PreguntasEncuesta();

        // Asigna un texto a la pregunta
        pregunta.setPregunta("¿Qué opinas de la asignatura?");

        // Verifica que el texto de la pregunta se haya asignado correctamente
        assertEquals("¿Qué opinas de la asignatura?", pregunta.getPregunta());
    }

    @Test
    void testAsignacionMateriaAPregunta() {
        // Crea instancias de PreguntasEncuesta y Materia
        PreguntasEncuesta pregunta = new PreguntasEncuesta();
        Materia materia = new Materia();

        // Asocia la materia a la pregunta
        pregunta.setMateria(materia);

        // Verifica que la materia se haya asignado correctamente
        assertNotNull(pregunta.getMateria());
    }
}
