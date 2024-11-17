package com.example.service;

import com.example.dao.PreguntasEncuestaDAO;
import com.example.model.PreguntasEncuesta;
import com.example.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class GetAllPreguntasEmptyTest {

    private PreguntasEncuestaDAO preguntasEncuestaDAO;
    private SessionFactory sessionFactory;

    @Before
    public void setUp() {
        sessionFactory = HibernateUtil.getSessionFactory();
        preguntasEncuestaDAO = new PreguntasEncuestaDAO();
        clearTestData();
    }

    @After
    public void tearDown() {
        clearTestData();  // Asegúrate de limpiar después de la prueba
    }

    private void clearTestData() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        // Eliminar todas las preguntas de encuesta
        session.createQuery("DELETE FROM PreguntasEncuesta").executeUpdate();

        session.getTransaction().commit();
        session.close();
    }

    @Test
    public void testGetAllPreguntasWhenEmpty() {
        List<PreguntasEncuesta> preguntas = preguntasEncuestaDAO.getAllPreguntas();

        // Verifica que la lista de preguntas esté vacía
        Assert.assertNotNull(preguntas);
        Assert.assertTrue(preguntas.isEmpty()); // Debe ser verdadera porque no hay preguntas
    }
}
