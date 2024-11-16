package com.example.service;

import com.example.model.Materia;
import com.example.model.RespuestaEncuesta;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GuardarRespuestaServletTest {

    @Test
    void testCrearInstanciaRespuesta() {
        // Crea una instancia de RespuestaEncuesta
        RespuestaEncuesta respuesta = new RespuestaEncuesta();

        // Verifica que la instancia de RespuestaEncuesta no sea null al crearse
        assertNotNull(respuesta);

        // Verifica que la calificación esté en su valor inicial (0) al crear la instancia
        assertEquals(0, respuesta.getCalificacion());
    }

    @Test
    void testAsignacionCalificacion() {
        // Crea una instancia de RespuestaEncuesta
        RespuestaEncuesta respuesta = new RespuestaEncuesta();

        // Asigna una calificación a la respuesta
        respuesta.setCalificacion(5);

        // Verifica que la calificación se haya asignado correctamente
        assertEquals(5, respuesta.getCalificacion());
    }

    @Test
    void testAsignacionMateriaARespuesta() {
        // Crea instancias de RespuestaEncuesta y Materia
        RespuestaEncuesta respuesta = new RespuestaEncuesta();
        Materia materia = new Materia();

        // Asocia la materia a la respuesta
        //respuesta.setCodigomateria(materia.getCodigomateria());

        // Verifica que el código de materia se haya asignado correctamente (por defecto será 0)
        //assertEquals(0, respuesta.getCodigomateria());
    }

    @Test
    void testAsignacionIDsRespuesta() {
        // Crea una instancia de RespuestaEncuesta
        RespuestaEncuesta respuesta = new RespuestaEncuesta();

        // Asigna valores de IDs
       // respuesta.setAlumnoId(1);
        //respuesta.setTutorId(2);
        //respuesta.setSolicitudId(3);

        // Verifica que los IDs se hayan asignado correctamente
        //assertEquals(1, respuesta.getAlumnoId());
        //assertEquals(2, respuesta.getTutorId());
        //assertEquals(3, respuesta.getSolicitudId());
    }
}
