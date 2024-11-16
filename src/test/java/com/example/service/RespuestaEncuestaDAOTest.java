package com.example.service;

import com.example.dao.RespuestaEncuestaDAO;
import com.example.model.RespuestaEncuesta;
import com.example.utils.HibernateUtil;
import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class RespuestaEncuestaDAOTest {

    private RespuestaEncuestaDAO respuestaEncuestaDAO;
    private RespuestaEncuesta respuesta;
    private final int idSolicitudTest = 1;

    @BeforeEach
    public void setUp() {
        // Inicializar el DAO y la respuesta de prueba
        respuestaEncuestaDAO = new RespuestaEncuestaDAO();

        // Crear una RespuestaEncuesta para las pruebas
        respuesta = new RespuestaEncuesta();
        //respuesta.setAlumnoId(123);
        //respuesta.setTutorId(456);
        //respuesta.setSolicitudId(idSolicitudTest);
        //respuesta.setCodigomateria(789);
        respuesta.setCalificacion(5);

        // Guardar la respuesta en la base de datos
        respuestaEncuestaDAO.saveRespuesta(respuesta);
    }

    @AfterEach
    public void tearDown() {
        // Limpiar la base de datos después de cada prueba
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            RespuestaEncuesta respuestaGuardada = session.get(RespuestaEncuesta.class, respuesta.getId());
            if (respuestaGuardada != null) {
                session.delete(respuestaGuardada);
            }
            session.getTransaction().commit();
        }
    }




}